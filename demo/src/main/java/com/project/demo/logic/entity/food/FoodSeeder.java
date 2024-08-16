package com.project.demo.logic.entity.food;


import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Component
public class FoodSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final FoodRepository foodRepository;
    public FoodSeeder(FoodRepository foodRepository){
        this.foodRepository = foodRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.loadFood();
    }

    private void loadFood(){
        FoodEnum[] foodNames = new FoodEnum[] { FoodEnum.COOKIES, FoodEnum.PIZZA, FoodEnum.FISH, };
        Map<FoodEnum, Long> FoodValueMap = Map.of(
                FoodEnum.COOKIES, 5L,
                FoodEnum.PIZZA, 10L,
                FoodEnum.FISH, 15L
        );

        Arrays.stream(foodNames).forEach(foodName -> {
            Optional<Food> optionalFood = foodRepository.findByName(foodName);

            optionalFood.ifPresentOrElse(System.out::println, () -> {
                Food foodToCreate = new Food();
                foodToCreate.setName(foodName);
                foodToCreate.setValue(FoodValueMap.get(foodName));
                foodRepository.save(foodToCreate);
            });
        });
    }
}
