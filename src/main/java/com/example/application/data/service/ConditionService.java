package com.example.application.data.service;

import com.example.application.data.entity.Conditions;
import com.example.application.data.repository.ConditionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConditionService {

    private final ConditionRepository conditionRepository;

    public ConditionService(ConditionRepository conditionRepository){
        this.conditionRepository = conditionRepository;
    }

    public List<Conditions> findAllConditions(String filterText){
        if (filterText == null || filterText.isEmpty()){
            return conditionRepository.findAll();
        }else {
            return conditionRepository.search(filterText);
        }
    }

    public long countConditions(){
        return conditionRepository.count();
    }

    public void deleteCondition(Conditions conditions){
        conditionRepository.delete(conditions);
    }

    public void saveCondition(Conditions conditions){
        if (conditions == null){
            System.err.println("Conditions is null");
        }
        conditionRepository.save(conditions);
    }

}
