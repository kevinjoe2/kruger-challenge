package com.kchamorro.krugerchallenge.repository;

import com.kchamorro.krugerchallenge.entity.VaccineTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VaccineTypeRepository extends JpaRepository<VaccineTypeEntity, Long> {
}
