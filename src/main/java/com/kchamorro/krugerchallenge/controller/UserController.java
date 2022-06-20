package com.kchamorro.krugerchallenge.controller;

import com.kchamorro.krugerchallenge.entity.UserEntity;
import com.kchamorro.krugerchallenge.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController @AllArgsConstructor
@RequestMapping(path = "/api/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserEntity>> get(){
        return ResponseEntity.ok().body(userService.get());
    }

    @PostMapping
    public ResponseEntity<UserEntity> save(@RequestBody UserEntity userEntity){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/save").toUriString());
        return ResponseEntity.created(uri).body(userService.save(userEntity));
    }

}
