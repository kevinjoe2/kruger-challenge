package com.kchamorro.krugerchallenge.repository;

import com.kchamorro.krugerchallenge.entity.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<ContactEntity, Long> {
    @Override
    List<ContactEntity> findAllById(Iterable<Long> longs);
}
