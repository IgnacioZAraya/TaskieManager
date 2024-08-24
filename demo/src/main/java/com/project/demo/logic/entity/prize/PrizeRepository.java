package com.project.demo.logic.entity.prize;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrizeRepository extends JpaRepository<Prize, Long> {
    Optional<Prize> findByPriority(PrizeEnum priority);
}
