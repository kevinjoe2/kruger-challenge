package com.kchamorro.krugerchallenge.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class EmployeeInformationResponse implements Serializable {
    private String dateBirth;
    private String homeAddress;
    private String mobilePhone;
    private List<EmployeeInformationVaccineResponse> vaccines;
}