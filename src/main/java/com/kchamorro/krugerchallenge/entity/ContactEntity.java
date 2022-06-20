package com.kchamorro.krugerchallenge.entity;

import com.kchamorro.krugerchallenge.util.enumerator.ContactTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "contacts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ContactTypeEnum type;
    private String value;

    @ManyToOne(fetch = FetchType.EAGER)
    private PersonEntity personEntity;

}
