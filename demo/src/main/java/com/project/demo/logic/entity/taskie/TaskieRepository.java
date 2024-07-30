package com.project.demo.logic.entity.taskie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskieRepository extends JpaRepository<Taskie, Long> {
    @Query("SELECT new com.project.demo.logic.entity.taskie.TaskieDTO(t.id, t.specie, t.name, t.alive, t.user, t.experience, t.sprite, t.visible,t.life,t.cleanse,t.hunger,t.energy) FROM Taskie t WHERE t.user.id = :userId and t.visible = true")
    List<TaskieDTO> findByUser(@Param("userId") Long userId);
}
