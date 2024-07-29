package com.project.demo.logic.entity.cosmetic;


import com.project.demo.logic.entity.cosmetic.CosmeticRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Component
public class CosmeticSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final CosmeticRepository cosmeticRepository;

    public CosmeticSeeder(CosmeticRepository cosmeticRepository) {
        this.cosmeticRepository = cosmeticRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) { this.loadCosmetics();

    }
    private void loadCosmetics(){
        CosmeticEnum[] CosmeticNames = new CosmeticEnum[] {CosmeticEnum.FOOD,CosmeticEnum.FOOTBALL,CosmeticEnum.SHAMPOO};
        Map<CosmeticEnum, String> stringSpritesMap = Map.of(
                CosmeticEnum.FOOD, "../../../assets/cosmeticsV1/Food.png",
                CosmeticEnum.FOOTBALL, "../../../assets/cosmeticsV1/football.png",
                CosmeticEnum.SHAMPOO, "../../../assets/cosmeticsV1/shampoo.png"

        );

        Arrays.stream(CosmeticNames).forEach((CosmeticName) -> {
            Optional<Cosmetic> optionalCosmetic = cosmeticRepository.findByName(CosmeticName);

            optionalCosmetic.ifPresentOrElse(System.out::println, () -> {
                Cosmetic CosmeticToCreate = new Cosmetic();

                CosmeticToCreate.setName(CosmeticName);
                CosmeticToCreate.setSprite(stringSpritesMap.get(CosmeticName));

                cosmeticRepository.save(CosmeticToCreate);
            });
        });
    }
}