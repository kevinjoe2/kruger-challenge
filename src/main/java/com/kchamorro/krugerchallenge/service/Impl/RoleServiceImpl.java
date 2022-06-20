package com.kchamorro.krugerchallenge.service.Impl;

import com.kchamorro.krugerchallenge.entity.RoleEntity;
import com.kchamorro.krugerchallenge.repository.RoleRepository;
import com.kchamorro.krugerchallenge.service.RoleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @AllArgsConstructor @Transactional @Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public RoleEntity save(RoleEntity roleEntity) {
        return roleRepository.save(roleEntity);
    }

    @Override
    public void deleteAll() {
        roleRepository.deleteAll();
    }
}
