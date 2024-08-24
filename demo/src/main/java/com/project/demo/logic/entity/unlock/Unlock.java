package com.project.demo.logic.entity.unlock;

import com.project.demo.logic.entity.interactable.Interactable;
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

/*Reutilizar para el sistema de niveles del taskie*/
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "cosmetic_id", referencedColumnName = "id", nullable = false)
    private Interactable interactable;

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

    public Interactable getCosmetic() {
        return interactable;
    }

    public void setCosmetic(Interactable interactable) {
        this.interactable = interactable;
    }
}