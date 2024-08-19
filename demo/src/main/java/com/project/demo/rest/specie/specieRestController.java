package com.project.demo.rest.specie;

import com.project.demo.logic.entity.specie.Specie;
import com.project.demo.logic.entity.specie.SpecieDTO;
import com.project.demo.logic.entity.specie.SpecieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/specie")
public class specieRestController {


    @Autowired
    private SpecieRepository specieRepository;

    @GetMapping
    @PreAuthorize("hasAnyRole('ASSOCIATE', 'SUPER_ADMIN', 'BASE')")
    public List<Specie> getAllSpecie() {
        return (List<Specie>) specieRepository.findAll();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ASSOCIATE', 'SUPER_ADMIN', 'BASE')")
    public Specie addSpecie(@RequestBody Specie specie) {
        return specieRepository.save(specie);
    }

    @GetMapping("/{id}")
    public Specie getSpecieById(@PathVariable Long id) {
        return specieRepository.findById(id).orElseThrow(() -> new RuntimeException("Specie not found"));
    }

    @PostMapping("/addSpecie")
    public Specie addSpecie(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("file") MultipartFile file,
            @RequestParam("evolution") MultipartFile evolutionFile) {

        String directory = "D:\\Tarea_2\\TaskieTamer_Front\\src\\assets\\taskies";
        Path path = Paths.get(directory);

        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            String fileName = file.getOriginalFilename();
            Path filePath = path.resolve(fileName);
            Files.write(filePath, file.getBytes());

            String evolutionFileName = evolutionFile.getOriginalFilename();
            Path evolutionFilePath = path.resolve("evolution/" + evolutionFileName);
            Files.write(evolutionFilePath, evolutionFile.getBytes());

            Specie specie = new Specie();
            specie.setName(name);
            specie.setDescription(description);
            specie.setSprite("../../../assets/taskies/" + fileName);
            specie.setEvolution("../../../assets/taskies/evolution/" + evolutionFileName);

            return specieRepository.save(specie);

        } catch (IOException e) {
            throw new RuntimeException("Failed to save file or specie", e);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public Specie updateSpecie(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "evolution", required = false) MultipartFile evolutionFile) {

        Specie specie = specieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Specie not found"));

        specie.setName(name);
        specie.setDescription(description);

        String directory = "D:\\Tarea_2\\TaskieTamer_Front\\src\\assets\\taskies";
        Path path = Paths.get(directory);

        try {
            if (file != null) {

                String fileName = file.getOriginalFilename();
                Path filePath = path.resolve(fileName);
                Files.write(filePath, file.getBytes());
                specie.setSprite("../../../assets/taskies/" + fileName);
            }

            if (evolutionFile != null) {

                String evolutionFileName = evolutionFile.getOriginalFilename();
                Path evolutionFilePath = path.resolve("evolution/" + evolutionFileName);
                Files.write(evolutionFilePath, evolutionFile.getBytes());
                specie.setEvolution("../../../assets/taskies/evolution/" + evolutionFileName);
            }

            return specieRepository.save(specie);

        } catch (IOException e) {
            throw new RuntimeException("Failed to save file or specie", e);
        }
    }
    @DeleteMapping("/{id}")
    public void deleteSpecie(@PathVariable Long id) {
        specieRepository.deleteById(id);
    }
}
