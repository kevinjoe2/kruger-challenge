package com.kchamorro.krugerchallenge.entity;

import com.kchamorro.krugerchallenge.util.enumerator.EmployeeStatusEnum;
import lombok.*;

import javax.persistence.*;

@Entity(name = "employees")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue( value = "EMPLOYEE" )
@ToString
public class EmployeeEntity extends PersonEntity{

    private Double salary;
    private String workstation;
    @Enumerated(EnumType.STRING)
    private EmployeeStatusEnum status;
    @OneToOne(fetch = FetchType.LAZY)
    private UserEntity user;
}
