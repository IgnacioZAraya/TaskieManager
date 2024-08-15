package com.project.demo.logic.entity.LevelTaskie;

import com.project.demo.logic.entity.cosmetic.Cosmetic;
import com.project.demo.logic.entity.cosmetic.CosmeticRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

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
        int count = 1;

        while (count <= 20){
            Optional<TaskieLevel> optionalTaskieLevel = levelTaskieRepository.findByName("Level "+count);
            AtomicReference<Optional<Cosmetic>> optionalCosmetic = new AtomicReference<>(Optional.empty());

            int finalCount = count;
            optionalTaskieLevel.ifPresentOrElse(System.out::println, () ->{
                TaskieLevel taskieLevelToCreate = new TaskieLevel();

                taskieLevelToCreate.setName("Level "+ finalCount);

                switch (taskieLevelToCreate.getName()){
                    case "Level 5": {
                        optionalCosmetic.set(cosmeticRepository.findByName("3D_GLASSES"));

                        if (optionalCosmetic.get().isEmpty()){
                            return;
                        }

                        taskieLevelToCreate.setCosmetic(optionalCosmetic.get().get());
                        break;
                    }
                    case "Level 10": {
                        optionalCosmetic.set(cosmeticRepository.findByName("CLASSY_OUTFIT"));

                        if (optionalCosmetic.get().isEmpty()){
                            return;
                        }

                        taskieLevelToCreate.setCosmetic(optionalCosmetic.get().get());
                        break;
                    }
                    case "Level 15": {
                        optionalCosmetic.set(cosmeticRepository.findByName("BEACH_OUTFIT"));

                        if (optionalCosmetic.get().isEmpty()){
                            return;
                        }

                        taskieLevelToCreate.setCosmetic(optionalCosmetic.get().get());
                        break;
                    }
                    case "Level 20": {
                        optionalCosmetic.set(cosmeticRepository.findByName("ROYAL_OUTFIT"));

                        if (optionalCosmetic.get().isEmpty()){
                            return;
                        }

                        taskieLevelToCreate.setCosmetic(optionalCosmetic.get().get());
                        break;
                    }
                    default: {
                        taskieLevelToCreate.setCosmetic(null);
                        taskieLevelToCreate.setHasEvolution(false);
                        break;
                    }
                }

                taskieLevelToCreate.setHasEvolution(Objects.equals(taskieLevelToCreate.getName(), "Level 15"));

                levelTaskieRepository.save(taskieLevelToCreate);
            });

            count++;
        }

    }
}
