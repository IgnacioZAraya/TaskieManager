package com.project.demo.repositories;


import com.project.demo.logic.entity.level.Level;
import com.project.demo.logic.entity.levelTaskie.LevelTaskieRepository;
import com.project.demo.logic.entity.levelTaskie.TaskieLevel;
import com.project.demo.logic.entity.rol.Role;
import com.project.demo.logic.entity.rol.RoleEnum;
import com.project.demo.logic.entity.specie.Specie;
import com.project.demo.logic.entity.specie.SpecieRepository;
import com.project.demo.logic.entity.status.StatusRepository;
import com.project.demo.logic.entity.taskie.Taskie;
import com.project.demo.logic.entity.taskie.TaskieRepository;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {"spring.datasource.url=jdbc:mariadb://localhost:3369/classdb"})
public class TaskieRepositoryTest {

    @Autowired
    TaskieRepository taskieRepository;

    @Autowired
    SpecieRepository specieRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    LevelTaskieRepository levelTaskieRepository;

    @Test
    public void  TaskieRepository_GetByVisibility_ReturnTaskie(){
        Specie specie = specieRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Specie not found"));

        User user = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Taskie taskie = new Taskie();
        taskie.setName("TIGER");
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
        optionalLevel.ifPresent(taskie::setLvlTaskie);

        Taskie taskie2 = new Taskie();
        taskie2.setName("PIGLET");
        taskie2.setSpecie(specie);
        taskie2.setUser(user);
        taskie2.setLife(100);
        taskie2.setEnergy(100);
        taskie2.setCleanse(100);
        taskie2.setHunger(100);
        taskie2.setExperience(0L);
        taskie2.setVisible(false);
        taskie2.setStatus(statusRepository.findById(1L).orElseThrow(() -> new RuntimeException("Status not found")));

        Optional<TaskieLevel> optionalLevel2 = levelTaskieRepository.findByName("Level 1");
        optionalLevel2.ifPresent(taskie2::setLvlTaskie);


        userRepository.save(user);

        taskieRepository.save(taskie);
        taskieRepository.save(taskie2);

        List<Taskie> listTaskies = taskieRepository.findByVisibility();


        Assertions.assertNotNull(listTaskies);
        Assertions.assertNotEquals(listTaskies.size(), 0);
    }

    @Test
    public void  TaskieRepository_GetByUser_ReturnTaskie(){
        Specie specie = specieRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Specie not found"));

        User user = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("User not found"));

        User user2 = userRepository.findById(2L)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Taskie taskie = new Taskie();
        taskie.setName("TIGER");
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
        optionalLevel.ifPresent(taskie::setLvlTaskie);

        Taskie taskie2 = new Taskie();
        taskie2.setName("PIGLET");
        taskie2.setSpecie(specie);
        taskie2.setUser(user2);
        taskie2.setLife(100);
        taskie2.setEnergy(100);
        taskie2.setCleanse(100);
        taskie2.setHunger(100);
        taskie2.setExperience(0L);
        taskie2.setVisible(false);
        taskie2.setStatus(statusRepository.findById(1L).orElseThrow(() -> new RuntimeException("Status not found")));

        Optional<TaskieLevel> optionalLevel2 = levelTaskieRepository.findByName("Level 1");
        optionalLevel2.ifPresent(taskie2::setLvlTaskie);


        userRepository.save(user);

        taskieRepository.save(taskie);
        taskieRepository.save(taskie2);

        List<Taskie> listTaskies = taskieRepository.findByUser(1L);


        Assertions.assertNotNull(listTaskies);
        Assertions.assertNotEquals(listTaskies.size(), 0);
    }
}
