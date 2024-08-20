package com.project.demo.logic.entity.user;

public class UserDTO {

    private  Long id;
    private String name;
    private Long foodUser;
    private Long cleanerUser;

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

    public Long getFoodUser() {
        return foodUser;
    }

    public void setFoodUser(Long foodUser) {
        this.foodUser = foodUser;
    }

    public Long getCleanerUser() {
        return cleanerUser;
    }

    public void setCleanerUser(Long cleanerUser) {
        this.cleanerUser = cleanerUser;
    }

}