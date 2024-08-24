package com.project.demo.logic.entity.specie;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecieRepository extends CrudRepository<Specie, Long> {
    Optional<Specie> findByName(String name);
}
