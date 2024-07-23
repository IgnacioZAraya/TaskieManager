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
        CosmeticEnum[] CosmeticNames = new CosmeticEnum[] {CosmeticEnum.SOAP,CosmeticEnum.FOOTBALL,CosmeticEnum.SHAMPOO};
        Map<CosmeticEnum, String> stringSpritesMap = Map.of(
                CosmeticEnum.SOAP, "../../../assets/cosmetics/soap.png",
                CosmeticEnum.FOOTBALL, "../../../assets/cosmetics/football.png",
                CosmeticEnum.SHAMPOO, "../../../assets/cosmetics/shampoo.png"

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