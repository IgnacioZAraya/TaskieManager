package com.project.demo.logic.entity.level;

import com.project.demo.logic.entity.prize.Prize;
import com.project.demo.logic.entity.rol.Role;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "level")
@Entity
public class Level {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long value;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "prize_id", referencedColumnName = "id", nullable = false)
    private Prize prize;

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
