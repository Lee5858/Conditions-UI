package com.example.application.data.service;

import com.example.application.data.entity.AtomicCondition;
import com.example.application.data.repository.AtomicConditionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtomicConditionService {

    private final AtomicConditionRepository atomicConditionRepository;

    public AtomicConditionService(AtomicConditionRepository atomicConditionRepository){
        this.atomicConditionRepository = atomicConditionRepository;
    }

    public List<AtomicCondition> findAllAtomicConditions(){
        return atomicConditionRepository.findAll();
    }

    public void deleteAtomicCondition(AtomicCondition atomicCondition){
        atomicConditionRepository.delete(atomicCondition);
    }

    public void saveAtomicCondition(AtomicCondition atomicCondition){
        if (atomicCondition == null){
            System.err.println("Condition is null");
        }
        atomicConditionRepository.save(atomicCondition);
    }

}
