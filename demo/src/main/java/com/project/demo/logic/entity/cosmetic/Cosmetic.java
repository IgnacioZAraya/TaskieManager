package com.project.demo.logic.entity.cosmetic;

import com.project.demo.logic.entity.food.FoodEnum;
import jakarta.persistence.*;

@Table(name = "cosmetic")
@Entity
public class Cosmetic{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private CosmeticEnum name;
    private String sprite;

    public CosmeticEnum getName() {
        return name;
    }

    public void setName(CosmeticEnum name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSprite() {
        return sprite;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }
}

