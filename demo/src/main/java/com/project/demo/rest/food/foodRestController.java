package com.project.demo.rest.food;

import com.project.demo.logic.entity.food.Food;
import com.project.demo.logic.entity.food.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food")
public class foodRestController {
    @Autowired
    private FoodRepository FoodRepository;


    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'USER')")
    public List<Food> getAllFood() {
        return FoodRepository.findAll();
    }

    @PostMapping
    public Food addFood(@RequestBody Food food) {
        return FoodRepository.save(food);
    }

    @GetMapping("/{id}")
    public Food getFoodById(@PathVariable Long id) {
        return FoodRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @PutMapping("/{id}")
    public Food updateFood(@PathVariable Long id, @RequestBody Food food) {
        return FoodRepository.findById(id)
                .map(existingFood -> {
                    existingFood.setName(existingFood.getName());
                    existingFood.setValue(existingFood.getValue());
                    existingFood.setSpeciesFav(existingFood.getSpeciesFav());
                    return FoodRepository.save(existingFood);
                })
                .orElseGet(() -> {
                    food.setId(id);
                    return FoodRepository.save(food);
                });
    }
    @DeleteMapping("/{id}")
    public void deleteFood(@PathVariable Long id) {
        FoodRepository.deleteById(id);
    }
}
