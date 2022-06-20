package com.kchamorro.krugerchallenge.entity;

import com.kchamorro.krugerchallenge.util.enumerator.EmployeeStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity(name = "employees")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue( value = "EMPLOYEE" )
public class EmployeeEntity extends PersonEntity{

    private Double salary;
    private String workstation;
    @Enumerated(EnumType.STRING)
    private EmployeeStatusEnum status;
}
