package com.project.demo.logic.entity.level;

import com.project.demo.logic.entity.prize.Prize;
import com.project.demo.logic.entity.prize.PrizeEnum;
import com.project.demo.logic.entity.prize.PrizeRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class LevelSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final LevelRepository levelRepository;
    private final PrizeRepository prizeRepository;

    public LevelSeeder(LevelRepository levelRepository, PrizeRepository prizeRepository){
        this.levelRepository = levelRepository;
        this.prizeRepository = prizeRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.loadLevel();
    }

    private void loadLevel(){
        int count = 1;

        while (count <= 10){
            Optional<Level> optionalLevel = levelRepository.findByName("Level "+count);
            AtomicReference<Optional<Prize>> optionalPrize = new AtomicReference<>(Optional.empty());

            int finalCount = count;
            optionalLevel.ifPresentOrElse(System.out::println, () ->{
                Level levelToCreate = new Level();

                levelToCreate.setName("Level "+ finalCount);
                levelToCreate.setValue(1500L*finalCount);

                if (finalCount < 4){
                    optionalPrize.set(prizeRepository.findByPriority(PrizeEnum.LOW));

                    if (optionalPrize.get().isEmpty()) {
                        return;
                    }

                    levelToCreate.setPrize(optionalPrize.get().get());
                } else if (finalCount > 6 ) {
                    optionalPrize.set(prizeRepository.findByPriority(PrizeEnum.HIGH));

                    if (optionalPrize.get().isEmpty()) {
                        return;
                    }

                    levelToCreate.setPrize(optionalPrize.get().get());
                }else {
                    optionalPrize.set(prizeRepository.findByPriority(PrizeEnum.MEDIUM));

                    if (optionalPrize.get().isEmpty()) {
                        return;
                    }

                    levelToCreate.setPrize(optionalPrize.get().get());
                }

                levelRepository.save(levelToCreate);
            });

            count++;
        }
    }
}
