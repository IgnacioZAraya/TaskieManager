package com.project.demo.repositories;

import com.project.demo.logic.entity.level.Level;
import com.project.demo.logic.entity.level.LevelRepository;
import com.project.demo.logic.entity.levelTaskie.TaskieLevel;
import com.project.demo.logic.entity.prize.PrizeEnum;
import com.project.demo.logic.entity.prize.PrizeRepository;
import com.project.demo.logic.entity.specie.Specie;
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
public class LevelRepositoryTest {
    @Autowired
    LevelRepository levelRepository;

    @Autowired
    PrizeRepository prizeRepository;

    @Test
    public void  LevelRepository_GetByName_ReturnLevel(){

        Level level = new Level();
        level.setName("Level 50");
        level.setValue(50000L);
        level.setPrize(prizeRepository.findByPriority(PrizeEnum.HIGH).get());

        levelRepository.save(level);

        Optional<Level> savedLevel = levelRepository.findByName(level.getName());


        Assertions.assertNotNull(savedLevel);
        Assertions.assertNotEquals(savedLevel.get().getId(), 0);
    }
}
