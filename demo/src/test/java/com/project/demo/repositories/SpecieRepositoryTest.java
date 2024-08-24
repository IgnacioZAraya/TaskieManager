package com.project.demo.repositories;

import com.project.demo.logic.entity.levelTaskie.TaskieLevel;
import com.project.demo.logic.entity.specie.Specie;
import com.project.demo.logic.entity.specie.SpecieRepository;
import com.project.demo.logic.entity.taskie.Taskie;
import com.project.demo.logic.entity.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {"spring.datasource.url=jdbc:mariadb://localhost:3369/classdb"})
public class SpecieRepositoryTest {

    @Autowired
    SpecieRepository specieRepository;

    @Test
    public void  SpecieRepository_Getpecie_ReturnSpecie(){
        Specie specie = new Specie();
        specie.setName("Tiger");
        specie.setDescription("This animals live deep in the forrest of Taskbourg, waiting for tourist and local people o bring them some wheat to eat.");
        specie.setSprite("../../../assets/taskies/Tigre bb.png");
        specie.setEvolution("../../../assets/taskies/evolution/Tigre.png");

        specieRepository.save(specie);

        Optional<Specie> savedSpecie = specieRepository.findByName(specie.getName());

        Assertions.assertNotNull(savedSpecie);
        Assertions.assertNotEquals(savedSpecie.get().getId(), 0);
    }

}
