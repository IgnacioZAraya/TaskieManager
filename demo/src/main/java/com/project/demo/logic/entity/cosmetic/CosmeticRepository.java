package com.project.demo.logic.entity.cosmetic;

import com.project.demo.logic.entity.status.Status;
import com.project.demo.logic.entity.status.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CosmeticRepository extends  JpaRepository<Cosmetic, Long> {
    Optional<Cosmetic> findByName(CosmeticEnum name);
}
