package com.project.demo.rest.level;

import com.project.demo.logic.entity.level.Level;
import com.project.demo.logic.entity.level.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/level")
public class levelRestController {
    @Autowired
    private LevelRepository LevelRepository;


    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'USER')")
    public List<Level> getAllLevel() {
        return LevelRepository.findAll();
    }

    @PostMapping
    public Level addLevel(@RequestBody Level level) {
        return LevelRepository.save(level);
    }

    @GetMapping("/{id}")
    public Level getLevelById(@PathVariable Long id) {
        return LevelRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @PutMapping("/{id}")
    public Level updateLevel(@PathVariable Long id, @RequestBody Level level) {
        return LevelRepository.findById(id)
                .map(existingLevel -> {
                    existingLevel.setValue(existingLevel.getValue());
                    existingLevel.setPrize(existingLevel.getPrize());
                    return LevelRepository.save(existingLevel);
                })
                .orElseGet(() -> {
                    level.setId(id);
                    return LevelRepository.save(level);
                });
    }
    @DeleteMapping("/{id}")
    public void deleteLevel(@PathVariable Long id) {
        LevelRepository.deleteById(id);
    }
}
