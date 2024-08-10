package com.project.demo.logic.entity.specie;


public class SpecieDTO {
    private String name;
    private String description;
    private String sprite;  // Este campo contendrá la ruta de la imagen

    // Getters y Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
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