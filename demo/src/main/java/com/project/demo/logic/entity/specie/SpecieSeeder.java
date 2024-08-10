package com.project.demo.logic.entity.specie;


import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Component
public class SpecieSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final SpecieRepository specieRepository;

    public SpecieSeeder(SpecieRepository specieRepository) {
        this.specieRepository = specieRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) { this.loadSpecies();

    }
    private void loadSpecies() {
        String[] specieNames = new String[] {"AXOLOT", "COCODRILE", "WOLF"};

        Map<String, String> stringDescriptionMap = Map.of(
                "AXOLOT", "A shimmering aquatic guardian that heals and purifies with its presence.",
                "COCODRILE", "An emerald guardian that controls currents and weather with its roar.",
                "WOLF", "A noble creature with silver fur whose howl summons the moon."

        );

        Map<String, String> stringSpriteMap = Map.of(
                "AXOLOT", "../../../assets/taskies/Ajolote bb.png",
                "COCODRILE", "../../../assets/taskies/Croco bb.png",
                "WOLF", "../../../assets/taskies/Lobo bb.png"
        );

        Arrays.stream(specieNames).forEach(specieName -> {
            Optional<Specie> optionalSpecie = specieRepository.findByName(specieName);

            if (optionalSpecie.isEmpty()) {
                Specie specieToCreate = new Specie();
                specieToCreate.setName(specieName);
                specieToCreate.setDescription(stringDescriptionMap.get(specieName));
                specieToCreate.setSprite(stringSpriteMap.get(specieName));

                specieRepository.save(specieToCreate);
            }
        });
    }
}