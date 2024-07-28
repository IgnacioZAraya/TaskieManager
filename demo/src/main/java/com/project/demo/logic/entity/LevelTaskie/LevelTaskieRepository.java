package com.project.demo.logic.entity.LevelTaskie;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LevelTaskieRepository extends JpaRepository<TaskieLevel, Integer> {
    Optional<TaskieLevel> findByName(LevelEnum levelEnum);
    Optional<TaskieLevel> findById(Integer id);
}
