package com.project.demo.rest.specie;

import com.project.demo.logic.entity.specie.Specie;
import com.project.demo.logic.entity.specie.SpecieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/specie")
public class specieRestController {
    @Autowired
    private SpecieRepository specieRepository;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'USER')")
    public List<Specie> getAllSpecie() {
        return (List<Specie>) specieRepository.findAll();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'USER')")
    public Specie addSpecie(@RequestBody Specie specie) {
        return specieRepository.save(specie);
    }

    @GetMapping("/{id}")
    public Specie getSpecieById(@PathVariable Long id) {
        return specieRepository.findById(id).orElseThrow(() -> new RuntimeException("Specie not found"));
    }

    @PutMapping("/{id}")
    public Specie updateSpecie(@PathVariable Long id, @RequestBody Specie specie) {
        return specieRepository.findById(id)
                .map(existingSpecie -> {
                    existingSpecie.setName(specie.getName());
                    existingSpecie.setDescription(specie.getDescription());
                    existingSpecie.setSprite(specie.getSprite());
                    return specieRepository.save(existingSpecie);
                })
                .orElseGet(() -> {
                    specie.setId(id);
                    return specieRepository.save(specie);
                });
    }

    @DeleteMapping("/{id}")
    public void deleteSpecie(@PathVariable Long id) {
        specieRepository.deleteById(id);
    }
}
