package com.project.demo.logic.entity.interactable;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InteractableRepository extends  JpaRepository<Interactable, Long> {
    Optional<Interactable> findByName(InteractableEnum name);
}
