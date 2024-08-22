package com.project.demo.rest.taskie;

import com.project.demo.logic.entity.interactable.Interactable;
import com.project.demo.logic.entity.interactable.InteractableRepository;
import com.project.demo.logic.entity.levelTaskie.LevelTaskieRepository;
import com.project.demo.logic.entity.levelTaskie.TaskieLevel;
import com.project.demo.logic.entity.specie.Specie;

import com.project.demo.logic.entity.specie.SpecieRepository;
import com.project.demo.logic.entity.status.StatusRepository;
import com.project.demo.logic.entity.taskie.Taskie;
import com.project.demo.logic.entity.taskie.TaskieDTO;
import com.project.demo.logic.entity.taskie.TaskieRepository;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @Autowired
    private LevelTaskieRepository levelTaskieRepository;



    @GetMapping
    @PreAuthorize("hasAnyRole('BASE', 'SUPER_ADMIN', 'ASSOCIATE')")
    public List<Taskie> getAllTaskie() {
        return taskieRepository.findByVisibility();
    }

    @PostMapping
    public ResponseEntity<?> addTaskie(@RequestBody TaskieDTO taskieDTO) {
        // Busca la especie del Taskie
        Specie specie = specieRepository.findById(taskieDTO.getSpecieId())
                .orElseThrow(() -> new RuntimeException("Specie not found"));

        // Busca el usuario que será dueño del Taskie
        User user = userRepository.findById(taskieDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Crea un nuevo Taskie
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

        Optional<TaskieLevel> optionalLevel = levelTaskieRepository.findByName("Level 1");
        if (optionalLevel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Default level 'Level 1' not found");
        }

        taskie.setLvlTaskie(optionalLevel.get());
        userRepository.save(user);

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

        if (interactable.getDirtynessEffect() != 0) {
            taskie.setCleanse(Math.min(taskie.getCleanse() + interactable.getDirtynessEffect(), 100));
            increaseTaskieExperience(taskie, 15L);
        }

        if (interactable.getHungerEffect() != 0) {
            taskie.setHunger(Math.min(taskie.getHunger() + interactable.getHungerEffect(), 100));
            increaseTaskieExperience(taskie, 20L);
        }

        if (interactable.getEnergyEffect() != 0) {
            taskie.setEnergy(Math.min(taskie.getEnergy() + interactable.getEnergyEffect(), 100));
            increaseTaskieExperience(taskie, 10L);
        }

        return ResponseEntity.ok(taskie);
    }
    private void increaseTaskieExperience(Taskie taskie, Long experienceToAdd) {
        taskie.setExperience(taskie.getExperience() + experienceToAdd);
        updateTaskieExperienceAndLevel(taskie);
    }

    private void updateTaskieExperienceAndLevel(Taskie taskie) {
        TaskieLevel currentLevel = taskie.getLvlTaskie();
        Long experienceToAdd = taskie.getExperience();

        while (experienceToAdd >= currentLevel.getValue()) {
            experienceToAdd -= currentLevel.getValue();

            TaskieLevel nextLevel = levelTaskieRepository.findById(currentLevel.getId() + 1)
                    .orElseThrow(() -> new RuntimeException("Next level not found"));

            if (nextLevel.isHasEvolution()) {
                taskie.setEvolved(true);
            }

            if(nextLevel.getCosmetic() != null){
                taskie.getTaskieCosmetics().add(nextLevel.getCosmetic());
            }

            taskie.setLvlTaskie(nextLevel);
            currentLevel = nextLevel;
        }

        taskie.setExperience(experienceToAdd);
        taskieRepository.save(taskie);
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
