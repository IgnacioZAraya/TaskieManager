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
        Long userId = task.getUserId();  // Obtener el id de la categoría

        if (userId == null) {
            throw new IllegalArgumentException("User ID must not be null");
        }

        Optional<User> optionalUser = UserRepository.findById(userId);
        if (optionalUser.isPresent()) {
            task.setUser(optionalUser.get());  // Asignar la categoría encontrada al producto
            return TaskRepository.save(task);
        } else {
            throw new IllegalArgumentException("User not found with id: " + userId);
        }
    }

   /*@GetMapping("/userId/{id}")
   public List<TaskDTO> getTasksByUserId(Long userId) {
       return TaskRepository.findByUser(userId);
   }*/

    @GetMapping("/userId/{userId}")
    public ResponseEntity<List<TaskDTO>> getTasksByUserId(@PathVariable Long userId) {
        List<TaskDTO> tasks = TaskRepository.findByUser(userId);
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task task) {
        return TaskRepository.findById(id)
                .map(existingTask -> {
                    existingTask.setName(existingTask.getName());
                    existingTask.setPriority(existingTask.getPriority());
                    existingTask.setDescription(existingTask.getDescription());
                    existingTask.setStartDate(existingTask.getStartDate());
                    existingTask.setEndDate(existingTask.getEndDate());
                    existingTask.setCompleted(existingTask.getCompleted());
                    return TaskRepository.save(existingTask);
                })
                .orElseGet(() -> {
                    task.setId(id);
                    return TaskRepository.save(task);
                });
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        TaskRepository.deleteById(id);
    }


}
