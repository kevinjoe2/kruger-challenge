package com.kchamorro.krugerchallenge.service;

import com.kchamorro.krugerchallenge.entity.UserEntity;

import java.util.List;

public interface UserService {
    List<UserEntity> get();
    UserEntity save(UserEntity userEntity);
    void deleteAll();
}
