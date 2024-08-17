package com.project.demo.rest.taskieLevel;

import com.project.demo.logic.entity.LevelTaskie.LevelTaskieRepository;
import com.project.demo.logic.entity.LevelTaskie.TaskieLevel;
import com.project.demo.logic.entity.level.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/levelTaskie")
public class taskieLevelRestController {
    @Autowired
    private LevelTaskieRepository levelTaskieRepository;


    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'USER')")
    public List<TaskieLevel> getAllLevel() {
        return levelTaskieRepository.findAll();
    }

    @PostMapping
    public TaskieLevel addLevel(@RequestBody TaskieLevel level) {
        return levelTaskieRepository.save(level);
    }

    @GetMapping("/{id}")
    public TaskieLevel getLevelById(@PathVariable Long id) {
        return levelTaskieRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @PutMapping("/{id}")
    public TaskieLevel updateLevel(@PathVariable Long id, @RequestBody TaskieLevel level) {
        return levelTaskieRepository.findById(id)
                .map(existingLevel -> {
                    existingLevel.setValue(existingLevel.getValue());
                    return levelTaskieRepository.save(existingLevel);
                })
                .orElseGet(() -> {
                    level.setId(id);
                    return levelTaskieRepository.save(level);
                });
    }
    @DeleteMapping("/{id}")
    public void deleteLevel(@PathVariable Long id) {
        levelTaskieRepository.deleteById(id);
    }
}
