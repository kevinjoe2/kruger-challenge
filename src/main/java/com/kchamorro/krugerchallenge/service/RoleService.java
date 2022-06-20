package com.kchamorro.krugerchallenge.service;

import com.kchamorro.krugerchallenge.entity.RoleEntity;

public interface RoleService {
    RoleEntity save(RoleEntity roleEntity);
    void deleteAll();
    RoleEntity findByName(String name);
}
