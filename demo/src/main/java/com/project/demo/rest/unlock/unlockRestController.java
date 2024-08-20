package com.project.demo.rest.unlock;

import com.project.demo.logic.entity.unlock.Unlock;
import com.project.demo.logic.entity.unlock.UnlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unlock")
public class unlockRestController {
    @Autowired
    private UnlockRepository UnlockRepository;


    @GetMapping
    @PreAuthorize("hasAnyRole('ASSOCIATE', 'SUPER_ADMIN', 'BASE')")
    public List<Unlock> getAllUnlock() {
        return UnlockRepository.findAll();
    }

    @PostMapping
    public Unlock addUnlock(@RequestBody Unlock unlock) {
        return UnlockRepository.save(unlock);
    }

    @GetMapping("/{id}")
    public Unlock getUnlockById(@PathVariable Long id) {
        return UnlockRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @PutMapping("/{id}")
    public Unlock updateUnlock(@PathVariable Long id, @RequestBody Unlock unlock) {
        return UnlockRepository.findById(id)
                .map(existingUnlock -> {
                    existingUnlock.setTaskie(existingUnlock.getTaskie());
                    existingUnlock.setUnlocked(existingUnlock.getUnlocked());
                    existingUnlock.setCosmetic(existingUnlock.getCosmetic());
                    return UnlockRepository.save(existingUnlock);
                })
                .orElseGet(() -> {
                    unlock.setId(id);
                    return UnlockRepository.save(unlock);
                });
    }

    @DeleteMapping("/{id}")
    public void deleteUnlock(@PathVariable Long id) {
        UnlockRepository.deleteById(id);
    }



}
