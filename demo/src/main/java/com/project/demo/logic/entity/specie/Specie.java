package com.project.demo.logic.entity.specie;

import jakarta.persistence.*;

@Table(name = "specie")
@Entity
public class Specie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private SpecieEnum name;

    private String description;

    private String sprite;

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

    public String getSprite() {
        return sprite;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }
}
