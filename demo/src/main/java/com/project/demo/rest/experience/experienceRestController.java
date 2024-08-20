package com.project.demo.rest.experience;

import com.project.demo.logic.entity.experience.Experience;
import com.project.demo.logic.entity.experience.ExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/experience")
public class experienceRestController {
    @Autowired
    private ExperienceRepository ExperienceRepository;


    @GetMapping
    @PreAuthorize("hasAnyRole('ASSOCIATE', 'SUPER_ADMIN', 'BASE')")
    public List<Experience> getAllExperience() {
        return ExperienceRepository.findAll();
    }

    @PostMapping
    public Experience addExperience(@RequestBody Experience experience) {
        return ExperienceRepository.save(experience);
    }

    @GetMapping("/{id}")
    public Experience getExperienceById(@PathVariable Long id) {
        return ExperienceRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @PutMapping("/{id}")
    public Experience updateExperience(@PathVariable Long id, @RequestBody Experience experience) {
        return ExperienceRepository.findById(id)
                .map(existingExperience -> {
                    existingExperience.setName(existingExperience.getName());
                    existingExperience.setValue(existingExperience.getValue());
                    return ExperienceRepository.save(existingExperience);
                })
                .orElseGet(() -> {
                    experience.setId(id);
                    return ExperienceRepository.save(experience);
                });
    }



    @DeleteMapping("/{id}")
    public void deleteExperience(@PathVariable Long id) {
        ExperienceRepository.deleteById(id);
    }
}
