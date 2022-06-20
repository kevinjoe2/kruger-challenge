package com.kchamorro.krugerchallenge.repository;

import com.kchamorro.krugerchallenge.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
    PersonEntity findByIdentification(String identification);
}
