package com.project.demo.logic.entity.experience;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    Optional<Experience> findByName(ExperienceEnum name);
}
