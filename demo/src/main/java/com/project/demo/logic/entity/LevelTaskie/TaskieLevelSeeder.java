package com.project.demo.logic.entity.LevelTaskie;

import com.project.demo.logic.entity.cosmetic.Cosmetic;
import com.project.demo.logic.entity.cosmetic.CosmeticEnum;
import com.project.demo.logic.entity.cosmetic.CosmeticRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
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

    private void loadLevels(){
        LevelEnum[] levelEnums = new LevelEnum[] {LevelEnum.Nivel_1, LevelEnum.Nivel_2, LevelEnum.Nivel_3, LevelEnum.Nivel_4, LevelEnum.Nivel_5,LevelEnum.Nivel_6, LevelEnum.Nivel_7, LevelEnum.Nivel_8, LevelEnum.Nivel_9, LevelEnum.Nivel_10,LevelEnum.Nivel_11, LevelEnum.Nivel_12, LevelEnum.Nivel_13, LevelEnum.Nivel_14, LevelEnum.Nivel_15,LevelEnum.Nivel_16, LevelEnum.Nivel_17, LevelEnum.Nivel_18, LevelEnum.Nivel_19, LevelEnum.Nivel_20,LevelEnum.Nivel_21, LevelEnum.Nivel_22, LevelEnum.Nivel_23, LevelEnum.Nivel_24, LevelEnum.Nivel_25,};
        Map<LevelEnum, CosmeticEnum> stringCosmeticMap = Map.of(
          LevelEnum.Nivel_5, CosmeticEnum.FOOD,
          LevelEnum.Nivel_10,CosmeticEnum.FOOTBALL,
          LevelEnum.Nivel_15,CosmeticEnum.SHAMPOO

        );
        

        Arrays.stream(levelEnums).forEach((levelEnum -> {
            Optional<TaskieLevel> optionalTaskieLevel = levelTaskieRepository.findByName(levelEnum);
            Optional<Cosmetic> optionalCosmetic = cosmeticRepository.findByName(stringCosmeticMap.get(levelEnum));

            optionalTaskieLevel.ifPresentOrElse(System.out::println, () ->{
                TaskieLevel taskieLevelToCreate = new TaskieLevel();

                taskieLevelToCreate.setName(levelEnum);
                if (optionalCosmetic.isEmpty() ) {
                    taskieLevelToCreate.setCosmetic(null);
                }else{
                    taskieLevelToCreate.setCosmetic(optionalCosmetic.get());
                }

                if(taskieLevelToCreate.getName() != LevelEnum.Nivel_15) {
                    taskieLevelToCreate.setHasEvolution(false);
                }else{
                    taskieLevelToCreate.setHasEvolution(true);
                }

                levelTaskieRepository.save(taskieLevelToCreate);
            });

        }));

    }
}
