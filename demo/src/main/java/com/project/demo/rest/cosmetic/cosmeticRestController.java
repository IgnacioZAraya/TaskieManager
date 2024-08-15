package com.project.demo.rest.cosmetic;

import com.project.demo.logic.entity.cosmetic.Cosmetic;
import com.project.demo.logic.entity.cosmetic.CosmeticRepository;
import com.project.demo.logic.entity.interactable.Interactable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cosmetic")
public class cosmeticRestController {
    @Autowired
    private CosmeticRepository cosmeticRepository;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'USER')")
    public List<Cosmetic> getAllCosmetic() {
        return cosmeticRepository.findAll();
    }

    @PostMapping
    public Cosmetic addCosmetic(@RequestBody Cosmetic cosmetic) {

        return cosmeticRepository.save(cosmetic);
    }

    @GetMapping("/{id}")
    public Cosmetic getCosmeticById(@PathVariable Long id) {
        return cosmeticRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @PutMapping("/{id}")
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
    public void deleteCosmetic(@PathVariable Long id) {
        cosmeticRepository.deleteById(id);
    }


}
