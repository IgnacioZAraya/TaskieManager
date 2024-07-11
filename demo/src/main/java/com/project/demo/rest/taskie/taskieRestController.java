package com.project.demo.rest.taskie;

import com.project.demo.logic.entity.taskie.Taskie;
import com.project.demo.logic.entity.taskie.TaskieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/taskie")
public class taskieRestController {
    @Autowired
    private TaskieRepository TaskieRepository;


    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'USER')")
    public List<Taskie> getAllTaskie() {
        return TaskieRepository.findAll();
    }

    @PostMapping
    public Taskie addTaskie(@RequestBody Taskie taskie) {
        return TaskieRepository.save(taskie);
    }

    @GetMapping("/{id}")
    public Taskie getTaskieById(@PathVariable Long id) {
        return TaskieRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @PutMapping("/{id}")
    public Taskie updateTaskie(@PathVariable Long id, @RequestBody Taskie taskie) {
        return TaskieRepository.findById(id)
                .map(existingTaskie -> {
                    existingTaskie.setName(existingTaskie.getName());
                    existingTaskie.setStatus(existingTaskie.getStatus());
                    existingTaskie.setUnlock(existingTaskie.getUnlock());
                    existingTaskie.setExperience(existingTaskie.getExperience());
                    existingTaskie.setSprite(existingTaskie.getSprite());
                    existingTaskie.setCosmetic(existingTaskie.getCosmetic());
                    return TaskieRepository.save(existingTaskie);
                })
                .orElseGet(() -> {
                    taskie.setId(id);
                    return TaskieRepository.save(taskie);
                });
    }

    @DeleteMapping("/{id}")
    public void deleteTaskie(@PathVariable Long id) {
        TaskieRepository.deleteById(id);
    }

}
