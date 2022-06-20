package com.kchamorro.krugerchallenge.repository;

import com.kchamorro.krugerchallenge.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    @Query("select e " +
            "from employees e " +
            "inner join users u " +
            "on e.user.id = u.id " +
            "where u.username = :username")
    EmployeeEntity findByUsername(String username);

}
