package com.project.demo.rest.taskie;

import com.project.demo.logic.entity.specie.Specie;
import com.project.demo.logic.entity.specie.SpecieRepository;
import com.project.demo.logic.entity.status.Status;
import com.project.demo.logic.entity.status.StatusRepository;
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
    private TaskieRepository taskieRepository;

    @Autowired
    private SpecieRepository specieRepository;

    @Autowired
    private StatusRepository statusRepository;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'USER')")
    public List<Taskie> getAllTaskie() {
        return taskieRepository.findAll();
    }

    @PostMapping
    public Taskie addTaskie(@RequestBody Taskie taskie) {
        Specie specie = specieRepository.findById(taskie.getSpecie().getId())
                .orElseThrow(() -> new RuntimeException("Specie not found"));
        taskie.setSpecie(specie);
        Status status = statusRepository.findById(taskie.getStatus().getId())
                .orElseThrow(() -> new RuntimeException("Status not found"));
        taskie.setStatus(status);

        return taskieRepository.save(taskie);
    }

    @GetMapping("/{id}")
    public Taskie getTaskieById(@PathVariable Long id) {
        return taskieRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @PutMapping("/{id}")
    public Taskie updateTaskie(@PathVariable Long id, @RequestBody Taskie taskie) {
        return taskieRepository.findById(id)
                .map(existingTaskie -> {
                    existingTaskie.setName(taskie.getName());
                    existingTaskie.setSpecie(specieRepository.findById(taskie.getSpecie().getId())
                            .orElseThrow(() -> new RuntimeException("Specie not found")));
                    existingTaskie.setStatus(statusRepository.findById(taskie.getStatus().getId())
                            .orElseThrow(() -> new RuntimeException("Status not found")));
                    existingTaskie.setExperience(taskie.getExperience());
                    existingTaskie.setSprite(taskie.getSprite());
                    return taskieRepository.save(existingTaskie);
                })
                .orElseGet(() -> {
                    taskie.setId(id);
                    return taskieRepository.save(taskie);
                });
    }

    @DeleteMapping("/{id}")
    public void deleteTaskie(@PathVariable Long id) {
        taskieRepository.deleteById(id);
    }
}