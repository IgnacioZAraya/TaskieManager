package com.project.demo.logic.entity.taskie;

import com.project.demo.logic.entity.specie.Specie;
import com.project.demo.logic.entity.status.Status;
import com.project.demo.logic.entity.user.User;
import jakarta.persistence.*;

import java.util.List;


public class TaskieDTO {

    private Long id;


    private Specie specie;

    private String name;


    private Status alive;


    private User user;
    private Long experience;

    private String sprite;

    private boolean visible;
    private Integer life;

    private Integer cleanse;

    private Integer hunger;

    private Integer energy;

    public Status getAlive() {
        return alive;
    }

    public void setAlive(Status alive) {
        this.alive = alive;
    }

    public Integer getLife() {
        return life;
    }

    public void setLife(Integer life) {
        this.life = life;
    }

    public Integer getCleanse() {
        return cleanse;
    }

    public void setCleanse(Integer cleanse) {
        this.cleanse = cleanse;
    }

    public Integer getHunger() {
        return hunger;
    }

    public void setHunger(Integer hunger) {
        this.hunger = hunger;
    }

    public Integer getEnergy() {
        return energy;
    }

    public void setEnergy(Integer energy) {
        this.energy = energy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Specie getSpecie() {
        return specie;
    }

    public void setSpecie(Specie specie) {
        this.specie = specie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }


    public Long getExperience() {
        return experience;
    }

    public void setExperience(Long experience) {
        this.experience = experience;
    }

    public String getSprite() {
        return sprite;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }

    public TaskieDTO(Long id, Specie specie, String name, Status alive, User user, Long experience, String sprite, boolean visible, Integer life, Integer cleanse, Integer hunger, Integer energy) {
        this.id = id;
        this.specie = specie;
        this.name = name;
        this.alive = alive;
        this.user = user;
        this.experience = experience;
        this.sprite = sprite;
        this.visible = visible;
        this.life = life;
        this.cleanse = cleanse;
        this.hunger = hunger;
        this.energy = energy;
    }
}