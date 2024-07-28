package com.project.demo.logic.entity.level;

import com.project.demo.logic.entity.prize.Prize;
import com.project.demo.logic.entity.rol.Role;
import com.project.demo.logic.entity.rol.RoleEnum;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "level")
@Entity
public class Level {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private LevelEnum name;
    private Long value;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "prize_id", referencedColumnName = "id")
    private Prize prize;

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

    public Prize getPrize() {
        return prize;
    }

    public void setPrize(Prize prize) {
        this.prize = prize;
    }

    public Level() {
    }
}
