package com.project.demo.logic.entity.status;


import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Component
public class StatusSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final StatusRepository statusRepository;

    public StatusSeeder(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) { this.loadStatus();

    }
    private void loadStatus(){
        StatusEnum[] statusNames = new StatusEnum[] {StatusEnum.NORMAL,StatusEnum.HUNGRY,StatusEnum.UNHEALTHY,StatusEnum.SLEEPY};
        Map<StatusEnum, String> stringDescriptionMap = Map.of(
                StatusEnum.UNHEALTHY, "The taskie is unhealthy",
                StatusEnum.NORMAL, "The taskie is 0k",
                StatusEnum.SLEEPY, "The taskie is sleepy",
                StatusEnum.HUNGRY, "The taskie is hungry"
        );

        Arrays.stream(statusNames).forEach((statusName) -> {
            Optional<Status> optionalStatus = statusRepository.findByName(statusName);

            optionalStatus.ifPresentOrElse(System.out::println, () -> {
                Status statusToCreate = new Status();

                statusToCreate.setName(statusName);
                statusToCreate.setDescripcion(stringDescriptionMap.get(statusName));

                statusRepository.save(statusToCreate);
            });
        });
    }
}
