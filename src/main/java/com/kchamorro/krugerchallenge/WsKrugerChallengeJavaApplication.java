package com.kchamorro.krugerchallenge;

import com.kchamorro.krugerchallenge.entity.RoleEntity;
import com.kchamorro.krugerchallenge.entity.UserEntity;
import com.kchamorro.krugerchallenge.service.RoleService;
import com.kchamorro.krugerchallenge.service.UserService;
import com.kchamorro.krugerchallenge.util.enumerator.RoleEnum;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class WsKrugerChallengeJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(WsKrugerChallengeJavaApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserService userService, RoleService roleService){
        return args -> {
            userService.deleteAll();
            roleService.deleteAll();
            RoleEntity roleEntityAdm = roleService.save(new RoleEntity(null, RoleEnum.ROLE_ADMINISTRATOR.toString()));
            RoleEntity roleEntityEmp = roleService.save(new RoleEntity(null, RoleEnum.ROLE_EMPLOYEE.toString()));
            userService.save(new UserEntity(null,"kChamorro","123456", List.of(roleEntityAdm)));
            userService.save(new UserEntity(null,"jOrtega","123456", List.of(roleEntityEmp)));
        };
    }

}
