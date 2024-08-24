package com.project.demo.logic.entity.prize;

import com.project.demo.logic.entity.experience.Experience;
import com.project.demo.logic.entity.experience.ExperienceRepository;
import com.project.demo.logic.entity.experience.ExperienceEnum;
import com.project.demo.logic.entity.food.Food;
import com.project.demo.logic.entity.food.FoodRepository;
import com.project.demo.logic.entity.food.FoodEnum;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Component
public class PrizeSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final PrizeRepository prizeRepository;
    private final FoodRepository foodRepository;
    private final ExperienceRepository experienceRepository;

    public PrizeSeeder(PrizeRepository prizeRepository, FoodRepository foodRepository, ExperienceRepository experienceRepository) {
        this.prizeRepository = prizeRepository;
        this.foodRepository = foodRepository;
        this.experienceRepository = experienceRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.loadPrize();
    }

    private void loadPrize() {
        PrizeEnum[] prizePriorities = PrizeEnum.values();
        Map<PrizeEnum, Long> prizeValueMap = Map.of(
                PrizeEnum.LOW, 1L,
                PrizeEnum.MEDIUM, 2L,
                PrizeEnum.HIGH, 3L
        );

        FoodEnum[] foodEnums = FoodEnum.values();
        ExperienceEnum[] experienceEnums = ExperienceEnum.values();

        Food[] foods = new Food[foodEnums.length];
        Experience[] experiences = new Experience[experienceEnums.length];

        for (int i = 0; i < foodEnums.length; i++) {
            Optional<Food> optionalFood = foodRepository.findByName(foodEnums[i]);
            if (optionalFood.isPresent()) {
                foods[i] = optionalFood.get();
            } else {
                throw new RuntimeException("Food " + foodEnums[i] + " not found!");
            }
        }

        for (int i = 0; i < experienceEnums.length; i++) {
            Optional<Experience> optionalExperience = experienceRepository.findByName(experienceEnums[i]);
            if (optionalExperience.isPresent()) {
                experiences[i] = optionalExperience.get();
            } else {
                throw new RuntimeException("Experience " + experienceEnums[i] + " not found!");
            }
        }

        Arrays.stream(prizePriorities).forEach(prizePriority -> {
            Optional<Prize> optionalPrize = prizeRepository.findByPriority(prizePriority);

            optionalPrize.ifPresentOrElse(System.out::println, () -> {
                Prize prizeToCreate = new Prize();
                prizeToCreate.setPriority(prizePriority);
                prizeToCreate.setExperience(experiences[prizePriority.ordinal()]);
                prizeToCreate.setFood(foods[prizePriority.ordinal()]);
                prizeRepository.save(prizeToCreate);
            });
        });
    }
}