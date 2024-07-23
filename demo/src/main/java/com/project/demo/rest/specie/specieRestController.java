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
    private SpecieRepository SpecieRepository;


    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'USER')")
    public List<Specie> getAllSpecie() {
        return (List<Specie>) SpecieRepository.findAll();
    }

    @PostMapping
    public Specie addSpecie(@RequestBody Specie specie) {
        return SpecieRepository.save(specie);
    }

    @GetMapping("/{id}")
    public Specie getSpecieById(@PathVariable Long id) {
        return SpecieRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @PutMapping("/{id}")
    public Specie updateSpecie(@PathVariable Long id, @RequestBody Specie specie) {
        return SpecieRepository.findById(id)
                .map(existingSpecie -> {
                    existingSpecie.setName(existingSpecie.getName());
                    existingSpecie.setDescription(existingSpecie.getDescription());
                    return SpecieRepository.save(existingSpecie);
                })
                .orElseGet(() -> {
                    specie.setId(id);
                    return SpecieRepository.save(specie);
                });
    }
    @DeleteMapping("/{id}")
    public void deleteSpecie(@PathVariable Long id) {
        SpecieRepository.deleteById(id);
    }


}
