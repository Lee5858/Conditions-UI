package com.example.application.data.repository;

import com.example.application.data.entity.AtomicCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtomicConditionRepository extends JpaRepository<AtomicCondition, Long> {
}
