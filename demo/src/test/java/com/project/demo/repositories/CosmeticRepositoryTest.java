package com.project.demo.repositories;

import com.project.demo.logic.entity.cosmetic.Cosmetic;
import com.project.demo.logic.entity.cosmetic.CosmeticRepository;
import com.project.demo.logic.entity.level.Level;
import com.project.demo.logic.entity.level.LevelRepository;
import com.project.demo.logic.entity.prize.PrizeEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {"spring.datasource.url=jdbc:mariadb://localhost:3369/classdb"})
public class CosmeticRepositoryTest {
    @Autowired
    CosmeticRepository cosmeticRepository;

    @Test
    public void  CosmeticRepository_GetByName_ReturnCosmetic(){

        Cosmetic cosmetic = new Cosmetic();
        cosmetic.setName("POLICE_OUTFIT");
        cosmetic.setSprite("../../../assets/cosmeticsV1/Police.png");

        cosmeticRepository.save(cosmetic);

        Optional<Cosmetic> savedCosmetic = cosmeticRepository.findByName(cosmetic.getName());


        Assertions.assertNotNull(savedCosmetic);
        Assertions.assertNotEquals(savedCosmetic.get().getId(), 0);
    }
}
