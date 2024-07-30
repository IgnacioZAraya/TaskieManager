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
    public SpecieSeeder (SpecieRepository specieRepository) {
        this.specieRepository = specieRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) { this.loadSpecies();

    }

    private void loadSpecies(){
        SpecieEnum[] specieNames = new SpecieEnum[] {SpecieEnum.AXOLOT, SpecieEnum.CAT, SpecieEnum.COCODRILE, SpecieEnum.WOLF, SpecieEnum.ROBOT, SpecieEnum.SLOTH};
        Map<SpecieEnum, String> stringDescriptionMap = Map.of(
                SpecieEnum.CAT, "Its a Cat perfect for",
                SpecieEnum.AXOLOT, "Its a Axolot perfect for",
                SpecieEnum.SLOTH, "Its a Sloth perfect for",
                SpecieEnum.WOLF, "Its a Wolf perfecto for",
                SpecieEnum.ROBOT, "Its a Robot perfect for",
                SpecieEnum.COCODRILE, "Its a Crocodile perfect for"
        );

        Arrays.stream(specieNames).forEach((specieName) -> {
            Optional<Specie> optionalSpecie = specieRepository.findByName(specieName);

            optionalSpecie.ifPresentOrElse(System.out::println, () -> {
                Specie specieToCreate = new Specie();

                specieToCreate.setName(specieName);
                specieToCreate.setDescription(stringDescriptionMap.get(specieName));

                specieRepository.save(specieToCreate);
            });
        });
    }
}
