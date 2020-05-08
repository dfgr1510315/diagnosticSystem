package com.ljz.diagnostic_system.service;

import com.github.pagehelper.PageInfo;
import com.ljz.diagnostic_system.model.Disease;
import com.ljz.diagnostic_system.model.GetDisease;
import com.ljz.diagnostic_system.model.subDisease;

import java.util.List;

public interface DiseaseService {
    void addDisease(Disease disease);

    PageInfo getDisease(GetDisease getDisease);

    //List<Disease> getDisease(GetDisease getDisease);

    int deleteDisease(String id);

    String getInformation(String id);

    int diseaseUpdate(Disease disease);

    List<subDisease> diagnosisDisease(Disease disease);
}
