package com.project.demo.rest.cosmetic;

import com.project.demo.logic.entity.cosmetic.Cosmetic;
import com.project.demo.logic.entity.cosmetic.CosmeticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cosmetic")
public class cosmeticRestController {
    @Autowired
    private CosmeticRepository CosmeticRepository;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'USER')")
    public List<Cosmetic> getAllCosmetic() {
        return CosmeticRepository.findAll();
    }

    @PostMapping
    public Cosmetic addCosmetic(@RequestBody Cosmetic cosmetic) {

        return CosmeticRepository.save(cosmetic);
    }

    @GetMapping("/{id}")
    public Cosmetic getCosmeticById(@PathVariable Long id) {
        return CosmeticRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @PutMapping("/{id}")
    public Cosmetic updateCosmetic(@PathVariable Long id, @RequestBody Cosmetic cosmetic) {
        return CosmeticRepository.findById(id)
                .map(existingCosmetic -> {
                    existingCosmetic.setSprite(cosmetic.getSprite());
                    return CosmeticRepository.save(existingCosmetic);
                })
                .orElseGet(() -> {
                    cosmetic.setId(id);
                    return CosmeticRepository.save(cosmetic);
                });
    }



    @DeleteMapping("/{id}")
    public void deleteCosmetic(@PathVariable Long id) {
        CosmeticRepository.deleteById(id);
    }


}
