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
        StatusEnum[] statusNames = new StatusEnum[] {StatusEnum.ALIVE, StatusEnum.DEAD};
        Map<StatusEnum, String> stringDescriptionMap = Map.of(
                StatusEnum.ALIVE, "The taskie is alive!!! Keep it up!!!!",
                StatusEnum.DEAD, "The taskie is dead!!! Now you have to complete 10 task without failing in a row to get it back!"
        );


        Arrays.stream(statusNames).forEach((statusName) -> {
            Optional<Status> optionalStatus = statusRepository.findByName(statusName);

            optionalStatus.ifPresentOrElse(System.out::println, () -> {
                Status statusToCreate = new Status();

                statusToCreate.setName(statusName);
                statusToCreate.setDescription(stringDescriptionMap.get(statusName));


                statusRepository.save(statusToCreate);
            });
        });
    }
}
