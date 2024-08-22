package com.project.demo.rest.cosmetic;

import com.project.demo.logic.entity.cosmetic.Cosmetic;
import com.project.demo.logic.entity.cosmetic.CosmeticRepository;
import com.project.demo.logic.entity.interactable.Interactable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cosmetic")
public class cosmeticRestController {
    @Autowired
    private CosmeticRepository cosmeticRepository;

    @GetMapping
    @PreAuthorize("hasAnyRole('ASSOCIATE', 'SUPER_ADMIN', 'BASE')")
    public List<Cosmetic> getAllCosmetic() {
        return cosmeticRepository.findAll();
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> addCosmetic(@RequestBody Cosmetic cosmetic) {
       Cosmetic savedCosmetic =  cosmeticRepository.save(cosmetic);
        return ResponseEntity.ok(savedCosmetic);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ASSOCIATE', 'SUPER_ADMIN', 'BASE')")
    public Cosmetic getCosmeticById(@PathVariable Long id) {
        return cosmeticRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ASSOCIATE', 'SUPER_ADMIN', 'BASE')")
    public Cosmetic updateCosmetic(@PathVariable Long id, @RequestBody Cosmetic cosmetic) {
        return cosmeticRepository.findById(id)
                .map(existingCosmetic -> {
                    existingCosmetic.setSprite(cosmetic.getSprite());
                    return cosmeticRepository.save(existingCosmetic);
                })
                .orElseGet(() -> {
                    cosmetic.setId(id);
                    return cosmeticRepository.save(cosmetic);
                });
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public void deleteCosmetic(@PathVariable Long id) {
        cosmeticRepository.deleteById(id);
    }


}
