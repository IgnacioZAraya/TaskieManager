package com.project.demo.logic.entity.cosmetic;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CosmeticRepository  extends JpaRepository<Cosmetic, Long> {
    Optional<Cosmetic> findByName(String name);
}
