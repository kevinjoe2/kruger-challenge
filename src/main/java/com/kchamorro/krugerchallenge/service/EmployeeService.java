package com.kchamorro.krugerchallenge.service;

import com.kchamorro.krugerchallenge.dto.EmployeeInformationResponse;
import com.kchamorro.krugerchallenge.dto.EmployeeRequestDto;
import com.kchamorro.krugerchallenge.dto.EmployeeResponseDto;
import com.kchamorro.krugerchallenge.entity.EmployeeEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {
    List<EmployeeEntity> list();
    EmployeeEntity findById(Long id);
    EmployeeInformationResponse information(String token);
    EmployeeResponseDto save(EmployeeRequestDto employeeRequestDto);
    EmployeeEntity update(Long employeeId, EmployeeRequestDto employeeRequestDto);
    void delete(Long id);
}
