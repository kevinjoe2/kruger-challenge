package com.kchamorro.krugerchallenge.entity;

import lombok.*;

import javax.persistence.*;

@Entity(name = "vaccine_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VaccineTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private String name;

}
