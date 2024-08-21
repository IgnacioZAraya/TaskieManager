package com.project.demo.logic.entity.taskie;

import com.project.demo.logic.entity.cosmetic.Cosmetic;
import com.project.demo.logic.entity.specie.Specie;
import com.project.demo.logic.entity.status.Status;
import com.project.demo.logic.entity.user.User;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


public class TaskieDTO {

    private Long id;


    private Long specieId;

    private String name;


    private Long statusId;


    private Long userId;
    private Long experience;



    private boolean visible;
    private Integer life;

    private Integer cleanse;

    private Integer hunger;

    private Integer energy;

    private boolean evolved;

    private ArrayList<Cosmetic> cosmetics;

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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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


    public Long getSpecieId() {
        return specieId;
    }

    public void setSpecieId(Long specieId) {
        this.specieId = specieId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isEvolved() {
        return evolved;
    }

    public void setEvolved(boolean evolved) {
        this.evolved = evolved;
    }

    public ArrayList<Cosmetic> getCosmetics() {
        return cosmetics;
    }

    public void setCosmetics(ArrayList<Cosmetic> cosmetics) {
        this.cosmetics = cosmetics;
    }

    public TaskieDTO(Long id, Long specieId, String name, Long statusId, Long userId, Long experience, boolean visible, Integer life, Integer cleanse, Integer hunger, Integer energy, boolean evolved, ArrayList<Cosmetic> cosmetics) {
        this.id = id;
        this.specieId = specieId;
        this.name = name;
        this.statusId = statusId;
        this.userId = userId;
        this.experience = experience;
        this.visible = visible;
        this.life = life;
        this.cleanse = cleanse;
        this.hunger = hunger;
        this.energy = energy;
        this.evolved = evolved;
        this.cosmetics = cosmetics;
    }
}

