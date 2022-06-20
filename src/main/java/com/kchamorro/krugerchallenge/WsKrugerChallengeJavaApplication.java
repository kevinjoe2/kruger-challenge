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

            RoleEntity roleEntityAdm = roleService.findByName(RoleEnum.ROLE_ADMINISTRATOR.toString());
            if (roleEntityAdm==null){
                roleEntityAdm = roleService.save(new RoleEntity(null, RoleEnum.ROLE_ADMINISTRATOR.toString()));
            }
            RoleEntity roleEntityEmp = roleService.findByName(RoleEnum.ROLE_EMPLOYEE.toString());
            if (roleEntityEmp==null){
                roleEntityEmp = roleService.save(new RoleEntity(null, RoleEnum.ROLE_EMPLOYEE.toString()));
            }
            UserEntity kChamorro = userService.findByUsername("kChamorro");
            if (kChamorro==null) {
                userService.save(new UserEntity(null,"kChamorro","123456", List.of(roleEntityAdm)));
            }
            UserEntity jOrtega = userService.findByUsername("jOrtega");
            if (jOrtega==null) {
                userService.save(new UserEntity(null,"jOrtega","123456", List.of(roleEntityEmp)));
            }
        };
    }

}
