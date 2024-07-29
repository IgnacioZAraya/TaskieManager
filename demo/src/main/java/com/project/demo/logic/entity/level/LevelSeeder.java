package com.project.demo.logic.entity.level;

import com.project.demo.logic.entity.rol.RoleEnum;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class LevelSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final LevelRepository levelRepository;
    public LevelSeeder(LevelRepository levelRepository){
        this.levelRepository = levelRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.loadLevel();
    }

    private void loadLevel(){
        LevelUserEnum[] levelNames = new LevelUserEnum[] { LevelUserEnum.Level_1, LevelUserEnum.Level_2, LevelUserEnum.Level_3, LevelUserEnum.Level_4, LevelUserEnum.Level_5, LevelUserEnum.Level_6, LevelUserEnum.Level_7, LevelUserEnum.Level_8, LevelUserEnum.Level_9, LevelUserEnum.Level_10};
        Map<LevelUserEnum, Long> levelValueMap = Map.of(
                LevelUserEnum.Level_1, 100L,
                LevelUserEnum.Level_2, 200L,
                LevelUserEnum.Level_3, 300L,
                LevelUserEnum.Level_4, 400L,
                LevelUserEnum.Level_5, 500L,
                LevelUserEnum.Level_6, 600L,
                LevelUserEnum.Level_7, 700L,
                LevelUserEnum.Level_8, 800L,
                LevelUserEnum.Level_9, 900L,
                LevelUserEnum.Level_10, 1000L
        );

        Arrays.stream(levelNames).forEach(levelName -> {
            Optional<Level> optionalLevel = levelRepository.findByName(levelName);

            optionalLevel.ifPresentOrElse(System.out::println, () -> {
                Level levelToCreate = new Level();
                levelToCreate.setName(levelName);
                levelToCreate.setValue(levelValueMap.get(levelName));
                levelRepository.save(levelToCreate);
            });
        });
    }
}
