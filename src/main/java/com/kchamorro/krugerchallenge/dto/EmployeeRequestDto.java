package com.kchamorro.krugerchallenge.dto;

import com.sun.istack.NotNull;
import lombok.Getter;

@Getter
public class EmployeeRequestDto {
    @NotNull
    private String identificationCard;
    @NotNull
    private String names;
    @NotNull
    private String lastnames;
    @NotNull
    private String email;
}
