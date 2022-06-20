package com.kchamorro.krugerchallenge.controller;

import com.kchamorro.krugerchallenge.dto.EmployeeInformationResponse;
import com.kchamorro.krugerchallenge.dto.EmployeeRequestDto;
import com.kchamorro.krugerchallenge.dto.EmployeeResponseDto;
import com.kchamorro.krugerchallenge.entity.EmployeeEntity;
import com.kchamorro.krugerchallenge.service.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/employees")
@AllArgsConstructor
@Slf4j
public class EmployeeController {

    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeEntity>> list(){
        return ResponseEntity.ok().body(employeeService.list());
    }

    @GetMapping("/information")
    public ResponseEntity<EmployeeInformationResponse> information(
            @RequestHeader("Authorization") String authorization
    ){
        return ResponseEntity.ok().body(employeeService.information(authorization));
    }

    @PostMapping
    public ResponseEntity<EmployeeResponseDto> save(
            @Validated @RequestBody EmployeeRequestDto employeeRequestDto
    ){
        return ResponseEntity.ok().body(employeeService.save(employeeRequestDto));
    }

    @PutMapping("{employeeId}")
    public ResponseEntity<EmployeeEntity> update(
            @Validated @PathVariable Long employeeId,
            @Validated @RequestBody EmployeeRequestDto employeeRequestDto
    ){
        return ResponseEntity.ok().body(employeeService.update(employeeId, employeeRequestDto));
    }

    @DeleteMapping("{employeeId}")
    public ResponseEntity<Void> delete(
            @Validated @PathVariable Long employeeId
    ){
        employeeService.delete(employeeId);
        return ResponseEntity.ok().build();
    }

}
