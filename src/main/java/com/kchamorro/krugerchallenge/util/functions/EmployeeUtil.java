package com.kchamorro.krugerchallenge.util.functions;

import com.kchamorro.krugerchallenge.dto.EmployeeRequestDto;

public class EmployeeUtil {

    public static String generateUsername(EmployeeRequestDto employeeRequestDto){
        return (employeeRequestDto.getNames().toLowerCase()+"."+ employeeRequestDto.getLastnames().toLowerCase()).replace(" ","");
    }
    public static String generatePassword(EmployeeRequestDto employeeRequestDto){
        return (employeeRequestDto.getNames().toLowerCase()+"."+ employeeRequestDto.getLastnames().toLowerCase()).replace(" ","");
    }
}
