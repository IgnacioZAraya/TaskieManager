package com.project.demo.rest.interactable;

import com.project.demo.logic.entity.interactable.Interactable;
import com.project.demo.logic.entity.interactable.InteractableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interactable")
public class interactableRestController {
    @Autowired
    private InteractableRepository InteractableRepository;

    @GetMapping
    @PreAuthorize("hasAnyRole('BASE', 'SUPER_ADMIN', 'ASSOCIATE')")
    public List<Interactable> getAllCosmetic() {
        return InteractableRepository.findAll();
    }

    @PostMapping
    public Interactable addCosmetic(@RequestBody Interactable interactable) {

        return InteractableRepository.save(interactable);
    }

    @GetMapping("/{id}")
    public Interactable getCosmeticById(@PathVariable Long id) {
        return InteractableRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @PutMapping("/{id}")
    public Interactable updateCosmetic(@PathVariable Long id, @RequestBody Interactable interactable) {
        return InteractableRepository.findById(id)
                .map(existingCosmetic -> {
                    existingCosmetic.setSprite(interactable.getSprite());
                    return InteractableRepository.save(existingCosmetic);
                })
                .orElseGet(() -> {
                    interactable.setId(id);
                    return InteractableRepository.save(interactable);
                });
    }


    @DeleteMapping("/{id}")
    public void deleteCosmetic(@PathVariable Long id) {
        InteractableRepository.deleteById(id);
    }


}
