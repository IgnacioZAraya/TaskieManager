package com.project.demo.logic.entity.taskie;


import com.project.demo.logic.entity.LevelTaskie.TaskieLevel;
import com.project.demo.logic.entity.specie.Specie;
import com.project.demo.logic.entity.status.Status;
import com.project.demo.logic.entity.user.User;
import jakarta.persistence.*;

import java.util.List;

@Table(name = "taskie")
@Entity
public class Taskie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "specie_id", referencedColumnName = "id", nullable = false)
    private Specie specie;

    private String name;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "status_id", referencedColumnName = "id", nullable = false)
    private Status alive;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private boolean visible;

    private Long experience;

    private String sprite;

    private Integer life;

    private Integer cleanse;

    private Integer hunger;

    private Integer energy;

    @ManyToMany
    @JoinTable(name = "taskie_level", joinColumns = @JoinColumn(name = "taskie_id"), inverseJoinColumns = @JoinColumn(name = "level_id"))
    private List<TaskieLevel> lvlTaskie;





    public Taskie() {
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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



}