package com.kchamorro.krugerchallenge.entity;

import com.kchamorro.krugerchallenge.util.enumerator.ContactTypeEnum;
import lombok.*;

import javax.persistence.*;

@Entity(name = "contacts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
