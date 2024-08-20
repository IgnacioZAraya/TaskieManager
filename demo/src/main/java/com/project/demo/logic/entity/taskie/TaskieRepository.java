package com.project.demo.logic.entity.taskie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TaskieRepository extends JpaRepository<Taskie, Long> {
    @Query("SELECT t FROM Taskie t " + "LEFT JOIN FETCH t.taskieCosmetics " + "WHERE t.user.id = :userId AND t.visible = true")
    List<Taskie> findByUser(@Param("userId") Long userId);
}