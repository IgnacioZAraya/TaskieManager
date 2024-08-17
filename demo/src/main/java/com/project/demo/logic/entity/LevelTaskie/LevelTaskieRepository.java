package com.project.demo.logic.entity.LevelTaskie;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LevelTaskieRepository extends JpaRepository<TaskieLevel, Long> {
    Optional<TaskieLevel> findByName(String levelName);
    Optional<TaskieLevel> findById(Long id);
}
