
package com.project.demo.rest.taskie;

import com.project.demo.logic.entity.interactable.Interactable;
import com.project.demo.logic.entity.interactable.InteractableRepository;
import com.project.demo.logic.entity.specie.Specie;

import com.project.demo.logic.entity.specie.SpecieRepository;
import com.project.demo.logic.entity.status.StatusRepository;
import com.project.demo.logic.entity.taskie.Taskie;
import com.project.demo.logic.entity.taskie.TaskieDTO;
import com.project.demo.logic.entity.taskie.TaskieRepository;
import com.project.demo.logic.entity.user.User;
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
    private InteractableRepository interactableRepository;

    @GetMapping
    @PreAuthorize("hasAnyRole('BASE', 'SUPER_ADMIN', 'ASSOCIATE')")
    public List<Taskie> getAllTaskie() {
        return taskieRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Taskie> addTaskie(@RequestBody TaskieDTO taskieDTO) {
        Specie specie = specieRepository.findById(taskieDTO.getSpecieId())
                .orElseThrow(() -> new RuntimeException("Specie not found"));

        User user = userRepository.findById(taskieDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Taskie taskie = new Taskie();
        taskie.setName(taskieDTO.getName());
        taskie.setSpecie(specie);
        taskie.setUser(user);
        taskie.setLife(100);
        taskie.setEnergy(100);
        taskie.setCleanse(100);
        taskie.setHunger(100);
        taskie.setExperience(0L);
        taskie.setVisible(true);
        taskie.setStatus(statusRepository.findById(1L).orElseThrow(() -> new RuntimeException("Status not found")));
        taskie.setEvolved(false);

        Taskie savedTaskie = taskieRepository.save(taskie);
        return ResponseEntity.ok(savedTaskie);
    }


    @PutMapping("/{id}/apply-cosmetic")
    public ResponseEntity<Taskie> applyIntractable(@PathVariable Long id, @RequestBody Map<String, Long> request) {
        Long cosmeticId = request.get("cosmeticId");
        Taskie taskie = taskieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Taskie not found"));

        Interactable interactable = interactableRepository.findById(cosmeticId)
                .orElseThrow(() -> new RuntimeException("Cosmetic not found"));

        taskie.setCleanse(Math.min(taskie.getCleanse() + interactable.getDirtynessEffect(), 100));
        taskie.setHunger(Math.min(taskie.getHunger() + interactable.getHungerEffect(), 100));
        taskie.setEnergy(Math.min(taskie.getEnergy() + interactable.getEnergyEffect(), 100));

        Taskie updatedTaskie = taskieRepository.save(taskie);
        return ResponseEntity.ok(updatedTaskie);
    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<List<Taskie>> getTaskieByUser(@PathVariable Long userId) {
        List<Taskie> taskies = taskieRepository.findByUser(userId);
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
