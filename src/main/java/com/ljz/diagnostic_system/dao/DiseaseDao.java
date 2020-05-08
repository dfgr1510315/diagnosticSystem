package com.ljz.diagnostic_system.dao;

import com.ljz.diagnostic_system.model.Disease;
import com.ljz.diagnostic_system.model.GetDisease;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface DiseaseDao {
    void addDisease(Disease disease);

    List<Disease> getDisease(GetDisease getDisease);

    int deleteDisease(String id);

    String getInformation(String id);

    int diseaseUpdate(Disease disease);

    List<Disease> diagnosisDisease(Disease disease);
}
