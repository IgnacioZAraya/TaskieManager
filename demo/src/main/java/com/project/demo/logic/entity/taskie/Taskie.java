package com.project.demo.logic.entity.taskie;

import com.project.demo.logic.entity.cosmetic.Cosmetic;
import com.project.demo.logic.entity.levelTaskie.TaskieLevel;
import com.project.demo.logic.entity.specie.Specie;
import com.project.demo.logic.entity.status.Status;
import com.project.demo.logic.entity.user.User;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "taskie")
@Entity
public class Taskie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "specie_id", referencedColumnName = "id", nullable = false)
    private Specie specie;

    private String name;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "status_id", referencedColumnName = "id", nullable = false)
    private Status status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private boolean visible;

    private Long experience;

    private Integer life;

    private Integer cleanse;

    private Integer hunger;

    private Integer energy;

    private boolean evolved;

    @ManyToOne
    @JoinColumn(name = "level_id", referencedColumnName = "id")
    private TaskieLevel lvlTaskie;

    @ManyToMany
    @JoinTable( name = "taskie_cosmetics", joinColumns = @JoinColumn(name = "taskie_id"), inverseJoinColumns = @JoinColumn(name = "cosmetic_id"))
    private List<Cosmetic> taskieCosmetics = new ArrayList<>();

    public Taskie() {
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    public TaskieLevel getLvlTaskie() {
        return lvlTaskie;
    }

    public void setLvlTaskie(TaskieLevel lvlTaskie) {
        this.lvlTaskie = lvlTaskie;
    }

    public boolean isEvolved() {
        return evolved;
    }

    public void setEvolved(boolean evolved) {
        this.evolved = evolved;
    }

    public List<Cosmetic> getTaskieCosmetics() {
        return taskieCosmetics;
    }

    public void setTaskieCosmetics(List<Cosmetic> taskieCosmetics) {
        this.taskieCosmetics = taskieCosmetics;
    }
}
