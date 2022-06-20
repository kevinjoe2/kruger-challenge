package com.kchamorro.krugerchallenge.entity;

import com.kchamorro.krugerchallenge.util.enumerator.IdentificationTypeEnum;
import com.kchamorro.krugerchallenge.util.enumerator.VaccineStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;

@Entity(name = "persons")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn( name="person_type", discriminatorType = DiscriminatorType.STRING )
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private IdentificationTypeEnum identificationType;

    private String identification;
    private String firstName;
    private String middleName;
    private String firstSurname;
    private String SecondSurname;
    private LocalDate dateBirth;
    private String homeAddress;

    @Enumerated(EnumType.STRING)
    private VaccineStatusEnum vaccineStatus;

    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<VaccineEntity> vaccineEntities;

    @OneToMany(fetch = FetchType.LAZY)
    private Collection<ContactEntity> contacts;
}
