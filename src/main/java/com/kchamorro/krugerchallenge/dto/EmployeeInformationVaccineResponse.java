package com.kchamorro.krugerchallenge.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class EmployeeInformationVaccineResponse implements Serializable {
    private String statusVaccine;
    private String date;
    private String number;
}
