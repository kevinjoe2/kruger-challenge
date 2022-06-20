package com.kchamorro.krugerchallenge.repository;

import com.kchamorro.krugerchallenge.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
}
