package com.project.demo.logic.entity.food;

import com.project.demo.logic.entity.experience.ExperienceEnum;
import jakarta.persistence.*;

@Table(name = "food")
@Entity
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private FoodEnum name;

    private Long value;

    private Integer speciesFav;

    public Food() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FoodEnum getName() {
        return name;
    }

    public void setName(FoodEnum name) {
        this.name = name;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Integer getSpeciesFav() {
        return speciesFav;
    }

    public void setSpeciesFav(Integer speciesFav) {
        this.speciesFav = speciesFav;
    }
}
