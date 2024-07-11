package com.project.demo.logic.entity.specie;

import com.project.demo.logic.entity.experience.ExperienceEnum;
import jakarta.persistence.*;

@Table(name = "specie")
@Entity
public class Specie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private SpecieEnum name;

    private String description;

    public Specie() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SpecieEnum getName() {
        return name;
    }

    public void setName(SpecieEnum name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
