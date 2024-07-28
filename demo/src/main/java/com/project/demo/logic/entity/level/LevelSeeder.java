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
        LevelEnum[] levelNames = new LevelEnum[] { LevelEnum.Level_1, LevelEnum.Level_2, LevelEnum.Level_3, LevelEnum.Level_4, LevelEnum.Level_5, LevelEnum.Level_6, LevelEnum.Level_7, LevelEnum.Level_8, LevelEnum.Level_9, LevelEnum.Level_10};
        Map<LevelEnum, Long> levelValueMap = Map.of(
                LevelEnum.Level_1, 100L,
                LevelEnum.Level_2, 200L,
                LevelEnum.Level_3, 300L,
                LevelEnum.Level_4, 400L,
                LevelEnum.Level_5, 500L,
                LevelEnum.Level_6, 600L,
                LevelEnum.Level_7, 700L,
                LevelEnum.Level_8, 800L,
                LevelEnum.Level_9, 900L,
                LevelEnum.Level_10, 1000L
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
