
package com.project.demo.rest.taskie;

import com.project.demo.logic.entity.cosmetic.Cosmetic;
import com.project.demo.logic.entity.cosmetic.CosmeticRepository;
import com.project.demo.logic.entity.specie.SpecieRepository;
import com.project.demo.logic.entity.status.StatusRepository;
import com.project.demo.logic.entity.taskie.Taskie;
import com.project.demo.logic.entity.taskie.TaskieDTO;
import com.project.demo.logic.entity.taskie.TaskieRepository;
import com.project.demo.logic.entity.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/taskie")
public class taskieRestController {
    @Autowired
    private TaskieRepository taskieRepository;

    @Autowired
    private SpecieRepository specieRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CosmeticRepository cosmeticRepository;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'USER')")
    public List<Taskie> getAllTaskie() {
        return taskieRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Taskie> addTaskie(@RequestBody Taskie taskie) {
        taskie.setSpecie(specieRepository.findById(taskie.getSpecie().getId())
                .orElseThrow(() -> new RuntimeException("Specie not found")));

        taskie.setAlive(statusRepository.findById(taskie.getAlive().getId())
                .orElseThrow(() -> new RuntimeException("Status not found")));

        taskie.setUser(userRepository.findById(taskie.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found")));

        Taskie savedTaskie = taskieRepository.save(taskie);
        return ResponseEntity.ok(savedTaskie);
    }

    @PutMapping("/{id}/apply-cosmetic")
    public ResponseEntity<Taskie> applyCosmetic(@PathVariable Long id, @RequestBody Map<String, Long> request) {
        Long cosmeticId = request.get("cosmeticId");
        Taskie taskie = taskieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Taskie not found"));

        Cosmetic cosmetic = cosmeticRepository.findById(cosmeticId)
                .orElseThrow(() -> new RuntimeException("Cosmetic not found"));

        taskie.setCleanse(Math.min(taskie.getCleanse() + cosmetic.getDirtynessEffect(), 100));
        taskie.setHunger(Math.min(taskie.getHunger() + cosmetic.getHungerEffect(), 100));
        taskie.setEnergy(Math.min(taskie.getEnergy() + cosmetic.getEnergyEffect(), 100));

        Taskie updatedTaskie = taskieRepository.save(taskie);
        return ResponseEntity.ok(updatedTaskie);
    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<List<TaskieDTO>> GetTaskieByUser(@PathVariable Long userId) {
        List<TaskieDTO> taskies = taskieRepository.findByUser(userId);
        return ResponseEntity.ok(taskies);
    }

    @PutMapping("/{id}")
    public Taskie updateTaskie(@PathVariable Long id, @RequestBody Taskie taskie) {
        return taskieRepository.findById(id)
                .map(existingTaskie -> {
                    existingTaskie.setName(taskie.getName());
                    existingTaskie.setSpecie(specieRepository.findById(taskie.getSpecie().getId())
                            .orElseThrow(() -> new RuntimeException("Specie not found")));
                    existingTaskie.setLife(taskie.getLife());
                    existingTaskie.setHunger(taskie.getHunger());
                    existingTaskie.setCleanse(taskie.getCleanse());
                    existingTaskie.setEnergy(taskie.getEnergy());
                    existingTaskie.setExperience(taskie.getExperience());
                    existingTaskie.setSprite(taskie.getSprite());
                    return taskieRepository.save(existingTaskie);
                })
                .orElseGet(() -> {
                    taskie.setId(id);
                    return taskieRepository.save(taskie);
                });
    }

    @DeleteMapping("/{id}")
    public void deleteTaskie(@PathVariable Long id) {
        taskieRepository.deleteById(id);
    }
}


