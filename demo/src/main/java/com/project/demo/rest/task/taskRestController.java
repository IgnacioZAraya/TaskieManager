package com.project.demo.rest.task;


import com.project.demo.logic.entity.experience.Experience;
import com.project.demo.logic.entity.experience.ExperienceRepository;
import com.project.demo.logic.entity.food.Food;
import com.project.demo.logic.entity.food.FoodRepository;
import com.project.demo.logic.entity.level.Level;
import com.project.demo.logic.entity.level.LevelRepository;
import com.project.demo.logic.entity.prize.Prize;
import com.project.demo.logic.entity.prize.PrizeEnum;
import com.project.demo.logic.entity.prize.PrizeRepository;
import com.project.demo.logic.entity.task.Task;
import com.project.demo.logic.entity.task.TaskDTO;
import com.project.demo.logic.entity.task.TaskRepository;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/task")
public class taskRestController {
    @Autowired
    private TaskRepository TaskRepository;

    @Autowired
    private UserRepository UserRepository;

    @Autowired
    private PrizeRepository PrizeRepository;

    @Autowired
    private ExperienceRepository ExperienceRepository;

    @Autowired
    private FoodRepository FoodRepository;

    @Autowired
    private LevelRepository LevelRepository;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'USER')")
    public List<Task> getAllTask() {
        return TaskRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> addTask(@RequestBody Task task) {
        try {
            task.setVisible(true);
            task.setCompleted(false);
            task.setVerified(false);
            Long userId = task.getUserId();

            if (userId == null) {
                return new ResponseEntity<>("User ID must not be null", HttpStatus.BAD_REQUEST);
            }

            Optional<User> optionalUser = UserRepository.findById(userId);
            if (optionalUser.isPresent()) {
                task.setUser(optionalUser.get());

                if (task.getPriority() != null) {
                    PrizeEnum priorityEnum;
                    try {
                        priorityEnum = PrizeEnum.valueOf(task.getPriority().toUpperCase());
                    } catch (IllegalArgumentException e) {
                        return new ResponseEntity<>("Invalid priority: " + task.getPriority(), HttpStatus.BAD_REQUEST);
                    }

                    Prize prize = PrizeRepository.findByPriority(priorityEnum)
                            .orElseThrow(() -> new IllegalArgumentException("Prize not found for priority: " + task.getPriority()));
                    task.setPrize(prize);
                }

                Task savedTask = TaskRepository.save(task);

                if (task.getRecurrent() != null && !task.getRecurrent().equalsIgnoreCase("never") && task.getRepeatTimes() != null && task.getRepeatTimes() > 0) {
                    List<Task> recurringTasks = savedTask.generateRecurringTasks();
                    for (Task recurringTask : recurringTasks) {
                        recurringTask.setParentId(savedTask.getId());
                        recurringTask.setPrize(task.getPrize());
                    }
                    TaskRepository.saveAll(recurringTasks);
                }

                return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("User not found with id: " + userId, HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unknown internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/userId/{userId}")
    public ResponseEntity<List<TaskDTO>> getTasksByUserId(@PathVariable Long userId) {
        List<TaskDTO> tasks = TaskRepository.findByUser(userId);
        return ResponseEntity.ok(tasks);
    }
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<TaskDTO>> getHistoryByUserId(@PathVariable Long userId) {
        List<TaskDTO> tasks = TaskRepository.findHistoryByUser(userId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/nextTask/{userId}")
    public ResponseEntity<List<TaskDTO>> getNextTasksByUserId(@PathVariable Long userId) {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.HOUR, 4);
        Date fourHoursLater = calendar.getTime();

        List<TaskDTO> tasks = TaskRepository.findNextTasks(userId, now, fourHoursLater);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/futureTasks/{userId}")
    public ResponseEntity<List<TaskDTO>> getFutureTasksByUserId(@PathVariable Long userId) {
        Date now = new Date();
        List<TaskDTO> tasks = TaskRepository.findFutureTasks(userId, now);
        return ResponseEntity.ok(tasks);
    }
    @GetMapping("/verifiedTask/{userId}")
    public ResponseEntity<List<TaskDTO>> getCompletedTasksByUserId(@PathVariable Long userId) {
        Date now = new Date();

        List<TaskDTO> tasks = TaskRepository.findCompletedTasks(userId);
        return ResponseEntity.ok(tasks);
    }

    @PatchMapping("/complete/{id}")
    public ResponseEntity<Task> updateTaskCompletion(@PathVariable Long id, @RequestBody Task task) {
        return TaskRepository.findById(id)
                .map(existingTask -> {
                    existingTask.setCompleted(true);
                    Task updatedTask = TaskRepository.save(existingTask);
                    return ResponseEntity.ok(updatedTask);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/verified/{id}")
    public ResponseEntity<Task> updateTaskVerified(@PathVariable Long id) {
        return TaskRepository.findById(id)
                .map(existingTask -> {
                    // Actualizar el estado de verificación de la tarea
                    existingTask.setVerified(true);
                    Task updatedTask = TaskRepository.save(existingTask);

                    // Actualizar la experiencia y la comida del usuario
                    updateUserExperienceAndFood(existingTask);

                    return ResponseEntity.ok(updatedTask);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private void updateUserExperienceAndFood(Task task) {
        Long prizeId = task.getPrize().getId();
        Prize prize = PrizeRepository.findById(prizeId)
                .orElseThrow(() -> new RuntimeException("Prize not found"));

        Long experienceId = prize.getExperience().getId();
        Long foodId = prize.getFood().getId();

        Experience experience = ExperienceRepository.findById(experienceId)
                .orElseThrow(() -> new RuntimeException("Experience not found"));
        Food food = FoodRepository.findById(foodId)
                .orElseThrow(() -> new RuntimeException("Food not found"));

        Long experienceValue = experience.getValue();
        Long foodValue = food.getValue();

        User user = task.getUser();
        if (user != null) {
            user.setExperience(user.getExperience() + experienceValue);
            user.setFoodUser(user.getFoodUser() + foodValue);
            user.setCleanerUser(user.getCleanerUser() + 15);
            updateUserExperienceAndLevel(user);

            UserRepository.save(user);
        }
    }
    private void updateUserExperienceAndLevel(User user) {
        Level currentLevel = user.getLevel();
        Long remainingExperience = user.getExperience();

        while (remainingExperience >= currentLevel.getValue()) {
            remainingExperience -= currentLevel.getValue();

            Level nextLevel = LevelRepository.findById(currentLevel.getId() + 1)
                    .orElseThrow(() -> new RuntimeException("Next level not found"));

            user.setLevel(nextLevel);
            user.setExperience(0L);
            currentLevel = nextLevel;
        }

        user.setExperience(remainingExperience);

        UserRepository.save(user);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task task) {
        return TaskRepository.findById(id)
                .map(existingTask -> {
                    existingTask.setName(task.getName());
                    existingTask.setPriority(task.getPriority());
                    existingTask.setDescription(task.getDescription());
                    existingTask.setStartDate(task.getStartDate());
                    existingTask.setEndDate(task.getEndDate());
                    existingTask.setRecurrent(task.getRecurrent());
                    existingTask.setRepeatTimes(task.getRepeatTimes());
                    existingTask.setVisible(true);
                    existingTask.setPrize(existingTask.getPrize());
                    Task updatedTask = TaskRepository.save(existingTask);

                    if (updatedTask.getRecurrent() != null && !updatedTask.getRecurrent().equalsIgnoreCase("never") && updatedTask.getRepeatTimes() != null && updatedTask.getRepeatTimes() > 0) {
                        List<Task> recurringTasks = updatedTask.generateRecurringTasks();
                        TaskRepository.saveAll(recurringTasks);
                    }

                    return updatedTask;
                })
                .orElseGet(() -> {
                    task.setId(id);
                    return TaskRepository.save(task);
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        Optional<Task> optionalTask = TaskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setVisible(false);
            TaskRepository.save(task);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
