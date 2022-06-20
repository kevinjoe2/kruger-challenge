package com.kchamorro.krugerchallenge.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmployeeResponseDto {
    public String username;
    public String password;
}
