package com.project.demo.rest.task;


import com.project.demo.logic.entity.task.Task;
import com.project.demo.logic.entity.task.TaskDTO;
import com.project.demo.logic.entity.task.TaskRepository;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'USER')")
    public List<Task> getAllTask() {
        return TaskRepository.findAll();
    }

    @PostMapping
    public Task addTask(@RequestBody Task task) {
        task.setVisible(true);
        task.setCompleted(false);
        task.setVerified(false);
        Long userId = task.getUserId();
        if (userId == null) {
            throw new IllegalArgumentException("User ID must not be null");
        }
        Optional<User> optionalUser = UserRepository.findById(userId);
        if (optionalUser.isPresent()) {
            task.setUser(optionalUser.get());
            Task savedTask = TaskRepository.save(task);

            if (task.getRecurrent() != null && !task.getRecurrent().equalsIgnoreCase("never") && task.getRepeatTimes() != null && task.getRepeatTimes() > 0) {
                List<Task> recurringTasks = savedTask.generateRecurringTasks();
                for (Task recurringTask : recurringTasks) {
                    recurringTask.setParentId(savedTask.getId());
                }
                TaskRepository.saveAll(recurringTasks);
            }
            return savedTask;
        } else {
            throw new IllegalArgumentException("User not found with id: " + userId);
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
                    Task updatedTask = TaskRepository.save(existingTask);

                    TaskRepository.deleteByParentId(updatedTask.getId());

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
