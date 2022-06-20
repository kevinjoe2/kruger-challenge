package com.kchamorro.krugerchallenge.repository;

import com.kchamorro.krugerchallenge.entity.VaccineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VaccineRepository extends JpaRepository<VaccineEntity, Long> {
}
