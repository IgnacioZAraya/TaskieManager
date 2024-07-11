package com.project.demo.rest.task;


import com.project.demo.logic.entity.task.Task;
import com.project.demo.logic.entity.task.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class taskRestController {
    @Autowired
    private TaskRepository TaskRepository;


    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'USER')")
    public List<Task> getAllTask() {
        return TaskRepository.findAll();
    }

    @PostMapping
    public Task addTask(@RequestBody Task task) {
        return TaskRepository.save(task);
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return TaskRepository.findById(id).orElseThrow(RuntimeException::new);
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
