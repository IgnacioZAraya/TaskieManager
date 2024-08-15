package com.project.demo.logic.entity.LevelTaskie;

import com.project.demo.logic.entity.cosmetic.Cosmetic;
import jakarta.persistence.*;

@Table(name = "taskieLvl")
@Entity
public class TaskieLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @OneToOne
    @JoinColumn(name = "cosmetic_id", referencedColumnName = "id")
    private Cosmetic cosmetic;

    private Long value;
    private boolean hasEvolution;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private LevelEnum name;
    public TaskieLevel() {
    }

    public LevelEnum getName() {
        return name;
    }

    public void setName(LevelEnum name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Cosmetic getCosmetic() {
        return cosmetic;
    }

    public void setCosmetic(Cosmetic cosmetic) {
        this.cosmetic = cosmetic;
    }

    public boolean isHasEvolution() {
        return hasEvolution;
    }

    public void setHasEvolution(boolean hasEvolution) {
        this.hasEvolution = hasEvolution;
    }
}
