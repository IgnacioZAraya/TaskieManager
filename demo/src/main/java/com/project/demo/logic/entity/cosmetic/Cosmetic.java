package com.project.demo.logic.entity.cosmetic;

import com.project.demo.logic.entity.food.FoodEnum;
import jakarta.persistence.*;

@Table(name = "cosmetic")
@Entity
public class Cosmetic{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CosmeticEnum name;


    private int hungerEffect;
    private int dirtynessEffect;

    private int energyEffect;
    private int lifeEffect;

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

    public int getHungerEffect() {
        return hungerEffect;
    }

    public void setHungerEffect(int hungerEffect) {
        this.hungerEffect = hungerEffect;
    }

    public int getBoredomEffect() {
        return dirtynessEffect;
    }

    public void setBoredomEffect(int boredomEffect) {
        this.dirtynessEffect = boredomEffect;
    }

    public int getLifeEffect() {
        return lifeEffect;
    }

    public void setLifeEffect(int lifeEffect) {
        this.lifeEffect = lifeEffect;
    }

    public int getDirtynessEffect() {
        return dirtynessEffect;
    }

    public void setDirtynessEffect(int dirtynessEffect) {
        this.dirtynessEffect = dirtynessEffect;
    }

    public int getEnergyEffect() {
        return energyEffect;
    }

    public void setEnergyEffect(int energyEffect) {
        this.energyEffect = energyEffect;
    }
}

