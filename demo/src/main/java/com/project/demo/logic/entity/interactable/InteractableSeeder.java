package com.project.demo.logic.entity.interactable;


import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Component
public class InteractableSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final InteractableRepository interactableRepository;

    public InteractableSeeder(InteractableRepository interactableRepository) {
        this.interactableRepository = interactableRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) { this.loadCosmetics();

    }
    private void loadCosmetics(){
        InteractableEnum[] cosmeticNames = new InteractableEnum[] {InteractableEnum.SHAMPOO, InteractableEnum.FOOTBALL, InteractableEnum.FOOD};
        Map<InteractableEnum, String> stringSpritesMap = Map.of(
                InteractableEnum.FOOD, "../../../assets/cosmeticsV1/Food.png",
                InteractableEnum.FOOTBALL, "../../../assets/cosmeticsV1/Football.png",
                InteractableEnum.SHAMPOO, "../../../assets/cosmeticsV1/Shampoo.png"

        );
        Map<InteractableEnum, Integer> hungerEffectMap = Map.of(
                InteractableEnum.FOOD, 30,
                InteractableEnum.FOOTBALL, 0,
                InteractableEnum.SHAMPOO, 0
        );

        Map<InteractableEnum, Integer> energyEffectmap = Map.of(
                InteractableEnum.FOOD, 0,
                InteractableEnum.FOOTBALL, 30,
                InteractableEnum.SHAMPOO, 0
        );

        Map<InteractableEnum, Integer> dirtynessEffectMap = Map.of(
                InteractableEnum.FOOD, 0,
                InteractableEnum.FOOTBALL, 0,
                InteractableEnum.SHAMPOO, 30
        );


        Arrays.stream(cosmeticNames).forEach((cosmeticName) -> {
            Optional<Interactable> optionalCosmetic = interactableRepository.findByName(cosmeticName);

            optionalCosmetic.ifPresentOrElse(System.out::println, () -> {
                Interactable interactableToCreate = new Interactable();

                interactableToCreate.setName(cosmeticName);
                interactableToCreate.setSprite(stringSpritesMap.get(cosmeticName));
                interactableToCreate.setHungerEffect(hungerEffectMap.get(cosmeticName));
                interactableToCreate.setDirtynessEffect(dirtynessEffectMap.get(cosmeticName));
                interactableToCreate.setEnergyEffect(energyEffectmap.get(cosmeticName));

                interactableRepository.save(interactableToCreate);
            });
        });
    }
}
