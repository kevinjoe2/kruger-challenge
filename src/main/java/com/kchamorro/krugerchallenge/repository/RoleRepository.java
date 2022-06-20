package com.kchamorro.krugerchallenge.repository;

import com.kchamorro.krugerchallenge.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findFirstByName(String name);
}
