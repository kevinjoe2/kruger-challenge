package com.kchamorro.krugerchallenge.service.Impl;

import com.kchamorro.krugerchallenge.entity.UserEntity;
import com.kchamorro.krugerchallenge.mapper.GeneralMapper;
import com.kchamorro.krugerchallenge.repository.UserRepository;
import com.kchamorro.krugerchallenge.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @AllArgsConstructor @Transactional @Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findFirstByUsername(username);
        if (userEntity == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}", username);
            return GeneralMapper.userEntityToUserSecurity(userEntity);
        }
    }

    @Override
    public List<UserEntity> get() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userRepository.save(userEntity);
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }
}
