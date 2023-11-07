package com.example.application.data.repository;

import com.example.application.data.entity.Conditions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConditionRepository extends JpaRepository<Conditions, Long> {
    @Query("select c from Conditions c " +
            "where lower(c.accountingLineCode) like lower(concat('%', :searchTerm, '%')) ")
    List<Conditions> search(@Param("searchTerm") String searchTerm);
}
