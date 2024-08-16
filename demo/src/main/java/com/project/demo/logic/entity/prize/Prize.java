package com.project.demo.logic.entity.prize;

import com.project.demo.logic.entity.experience.Experience;
import com.project.demo.logic.entity.food.Food;
import jakarta.persistence.*;

@Table(name = "prize")
@Entity
public class Prize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private PrizeEnum priority;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "experience_id", referencedColumnName = "id", nullable = false)
    private Experience experience;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "food_id", referencedColumnName = "id", nullable = false)
    private Food food;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Experience getExperience() {
        return experience;
    }

    public void setExperience(Experience experience) {
        this.experience = experience;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public PrizeEnum getPriority() {
        return priority;
    }

    public void setPriority(PrizeEnum priority) {
        this.priority = priority;
    }

    public Prize() {
    }
}
