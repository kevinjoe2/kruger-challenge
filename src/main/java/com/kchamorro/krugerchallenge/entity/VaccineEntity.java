package com.kchamorro.krugerchallenge.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "vaccines")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VaccineEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private LocalDate date;
    private int numberDoses;

    @OneToOne(fetch = FetchType.EAGER)
    private VaccineTypeEntity type;
}
