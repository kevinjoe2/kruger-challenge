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
    public ResponseEntity<List<EmployeeInformationResponse>> list(){
        return ResponseEntity.ok().body(employeeService.list());
    }

    @GetMapping("/search")
    public ResponseEntity<List<EmployeeInformationResponse>> search(
            @RequestParam(value = "statusVaccine", required = false) String statusVaccine,
            @RequestParam(value = "typeVaccine", required = false) String typeVaccine,
            @RequestParam(value = "dateFromVaccine", required = false) String dateFromVaccine,
            @RequestParam(value = "dateToVaccine", required = false) String dateToVaccine
    ){
        log.info("statusVaccine:{}",statusVaccine);
        log.info("typeVaccine:{}",typeVaccine);
        log.info("dateFromVaccine:{}",dateFromVaccine);
        log.info("dateToVaccine:{}",dateToVaccine);
        return ResponseEntity.ok().body(employeeService.search(
                statusVaccine,typeVaccine,dateFromVaccine,dateToVaccine));
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

    @PostMapping("/saveEmployee")
    public ResponseEntity<EmployeeInformationResponse> save(
            @RequestHeader("Authorization") String authorization,
            @Validated @RequestBody EmployeeInformationResponse employeeRequestDto
    ){
        return ResponseEntity.ok().body(employeeService.saveEmployee(authorization, employeeRequestDto));
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
