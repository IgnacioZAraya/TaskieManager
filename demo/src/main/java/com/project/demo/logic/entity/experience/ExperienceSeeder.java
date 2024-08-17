package com.project.demo.logic.entity.experience;


import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Component
public class ExperienceSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final ExperienceRepository experienceRepository;
    public ExperienceSeeder(ExperienceRepository experienceRepository){
        this.experienceRepository = experienceRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.loadExperience();
    }

    private void loadExperience(){
        ExperienceEnum[] experienceNames = new ExperienceEnum[] { ExperienceEnum.LOW, ExperienceEnum.MEDIUM, ExperienceEnum.HIGH};
        Map<ExperienceEnum, Long> experienceValueMap = Map.of(
                ExperienceEnum.LOW, 5L,
                ExperienceEnum.MEDIUM, 15L,
                ExperienceEnum.HIGH, 25L
        );

        Arrays.stream(experienceNames).forEach(experienceName -> {
            Optional<Experience> optionalExperience = experienceRepository.findByName(experienceName);
            optionalExperience.ifPresentOrElse(System.out::println, () -> {
                Experience experienceToCreate = new Experience();
                experienceToCreate.setName(experienceName);
                experienceToCreate.setValue(experienceValueMap.get(experienceName));
                experienceRepository.save(experienceToCreate);
            });
        });
    }
}
