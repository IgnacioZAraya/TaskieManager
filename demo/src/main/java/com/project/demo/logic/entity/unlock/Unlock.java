package com.project.demo.logic.entity.unlock;

import com.project.demo.logic.entity.cosmetic.Cosmetic;
import com.project.demo.logic.entity.food.FoodEnum;
import com.project.demo.logic.entity.status.Status;
import com.project.demo.logic.entity.taskie.Taskie;
import jakarta.persistence.*;

@Table(name = "unlocks")
@Entity
public class Unlock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "taskie_id", referencedColumnName = "id", nullable = false)
    private Taskie taskie;

    private Boolean isUnlocked;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "cosmetic_id", referencedColumnName = "id", nullable = false)
    private Cosmetic cosmetic;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Taskie getTaskie() {
        return taskie;
    }

    public void setTaskie(Taskie taskie) {
        this.taskie = taskie;
    }

    public Boolean getUnlocked() {
        return isUnlocked;
    }

    public void setUnlocked(Boolean unlocked) {
        isUnlocked = unlocked;
    }

    public Cosmetic getCosmetic() {
        return cosmetic;
    }

    public void setCosmetic(Cosmetic cosmetic) {
        this.cosmetic = cosmetic;
    }
}