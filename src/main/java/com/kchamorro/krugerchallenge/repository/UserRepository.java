package com.kchamorro.krugerchallenge.repository;

import com.kchamorro.krugerchallenge.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findFirstByUsername(String username);
}
