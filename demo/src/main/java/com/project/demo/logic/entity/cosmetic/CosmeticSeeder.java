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
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) { this.loadCosmetics();

    }
    private void loadCosmetics(){
        CosmeticEnum[] cosmeticNames = new CosmeticEnum[] {CosmeticEnum.SOAP,CosmeticEnum.FOOTBALL,CosmeticEnum.SHAMPOO};
        Map<CosmeticEnum, String> stringSpritesMap = Map.of(
                CosmeticEnum.SOAP, "../../../assets/cosmetics/soap.png",
                CosmeticEnum.FOOTBALL, "../../../assets/cosmetics/football.png",
                CosmeticEnum.SHAMPOO, "../../../assets/cosmetics/shampoo.png"

        );
        Map<CosmeticEnum, Integer> hungerEffectMap = Map.of(
                CosmeticEnum.SOAP, 10,
                CosmeticEnum.FOOTBALL, 0,
                CosmeticEnum.SHAMPOO, 0
        );

        Map<CosmeticEnum, Integer> energyEffectmap = Map.of(
                CosmeticEnum.SOAP, 0,
                CosmeticEnum.FOOTBALL, 30,
                CosmeticEnum.SHAMPOO, 0
        );

        Map<CosmeticEnum, Integer> dirtynessEffectMap = Map.of(
                CosmeticEnum.SOAP, 0,
                CosmeticEnum.FOOTBALL, 0,
                CosmeticEnum.SHAMPOO, 30
        );


        Arrays.stream(cosmeticNames).forEach((cosmeticName) -> {
            Optional<Cosmetic> optionalCosmetic = cosmeticRepository.findByName(cosmeticName);

            optionalCosmetic.ifPresentOrElse(System.out::println, () -> {
                Cosmetic CosmeticToCreate = new Cosmetic();

                CosmeticToCreate.setName(cosmeticName);
                CosmeticToCreate.setSprite(stringSpritesMap.get(cosmeticName));
                CosmeticToCreate.setHungerEffect(hungerEffectMap.get(cosmeticName));
                CosmeticToCreate.setDirtynessEffect(dirtynessEffectMap.get(cosmeticName));
                CosmeticToCreate.setEnergyEffect(energyEffectmap.get(cosmeticName));

                cosmeticRepository.save(CosmeticToCreate);
            });
        });
    }
}