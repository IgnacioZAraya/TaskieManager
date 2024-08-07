package com.project.demo.logic.entity.LevelTaskie;

import com.project.demo.logic.entity.cosmetic.Cosmetic;
import com.project.demo.logic.entity.cosmetic.CosmeticEnum;
import com.project.demo.logic.entity.cosmetic.CosmeticRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
@Component
public class TaskieLevelSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final LevelTaskieRepository levelTaskieRepository;
    private final CosmeticRepository cosmeticRepository;


    public TaskieLevelSeeder(LevelTaskieRepository levelTaskieRepository, CosmeticRepository cosmeticRepository){
        this.levelTaskieRepository = levelTaskieRepository;
        this.cosmeticRepository = cosmeticRepository;
    }
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) { this.loadLevels();

    }

    private void loadLevels() {
        Map<LevelEnum, CosmeticEnum> levelCosmeticMap = Map.of(
                LevelEnum.Nivel_5, CosmeticEnum.FOOD,
                LevelEnum.Nivel_10, CosmeticEnum.FOOTBALL,
                LevelEnum.Nivel_15, CosmeticEnum.SHAMPOO
        );

        EnumMap<LevelEnum, Long> levelExperienceMap = new EnumMap<>(LevelEnum.class);
        for (int i = 0; i < LevelEnum.values().length; i++) {
            levelExperienceMap.put(LevelEnum.values()[i], (i + 1) * 25L);
        }

        Arrays.stream(LevelEnum.values()).forEach(levelEnum -> {
            Optional<TaskieLevel> optionalTaskieLevel = levelTaskieRepository.findByName(levelEnum);
            Optional<Cosmetic> optionalCosmetic = Optional.ofNullable(levelCosmeticMap.get(levelEnum))
                    .flatMap(cosmeticRepository::findByName);

            optionalTaskieLevel.ifPresentOrElse(
                    System.out::println,
                    () -> {
                        TaskieLevel taskieLevelToCreate = new TaskieLevel();
                        taskieLevelToCreate.setName(levelEnum);
                        taskieLevelToCreate.setValue(levelExperienceMap.get(levelEnum));
                        taskieLevelToCreate.setCosmetic(optionalCosmetic.orElse(null));
                        taskieLevelToCreate.setHasEvolution(levelEnum == LevelEnum.Nivel_15);
                        levelTaskieRepository.save(taskieLevelToCreate);
                    }
            );
        });
    }
}
