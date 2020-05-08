package com.ljz.diagnostic_system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ljz.diagnostic_system.common.Utils.ImageUtils;
import com.ljz.diagnostic_system.dao.DiseaseDao;
import com.ljz.diagnostic_system.model.Disease;
import com.ljz.diagnostic_system.model.GetDisease;
import com.ljz.diagnostic_system.model.subDisease;
import com.ljz.diagnostic_system.service.DiseaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiseaseServiceImpl implements DiseaseService {

    @Autowired
    private DiseaseDao diseaseDao;

    //@CachePut(value = "disease",key = "'diseaseList'")
    @Override
    public void addDisease(Disease disease) {
        diseaseDao.addDisease(disease);
    }

    @Override
    //@Cacheable(value = "disease",key = "'diseaseList'")
    public PageInfo getDisease(GetDisease getDisease) {
        PageHelper.startPage(getDisease.getPage(),getDisease.getPageSize());
        List<Disease> diseaseList = diseaseDao.getDisease(getDisease);
        return new PageInfo<>(diseaseList);
    }

/*    @Override
    //@Cacheable(value = "disease",key = "'diseaseList'")
    public List<Disease> getDisease(GetDisease getDisease) {
        return diseaseDao.getDisease(getDisease);
    }*/

    @Override
    //@CacheEvict(value = "disease",key = "'diseaseList'")
    @CacheEvict(value = "disease",key = "'diseaseInformation'+#id")
    public int deleteDisease(String id) {
        return diseaseDao.deleteDisease(id);
    }

    @Override
    @Cacheable(value = "disease",key = "'diseaseInformation'+#id")
    public String getInformation(String id) {
        return diseaseDao.getInformation(id);
    }

    @Override
    //@Cacheable(value = "disease",key = "'diseaseList'")
    @CacheEvict(value = "disease",key = "'diseaseInformation'+#disease.getId()")
    public int diseaseUpdate(Disease disease) {
        return diseaseDao.diseaseUpdate(disease);
    }

    @Override
    public List<subDisease> diagnosisDisease(Disease disease) {
        //System.out.println("----------------------"+disease);
        List<Disease> diseaseList = diseaseDao.diagnosisDisease(disease);
        List<subDisease> res = new ArrayList<>();
        for (Disease disease1:diseaseList){
            String[] imgs = disease1.getImage().split("\\|");
            for (String img : imgs){
                double similarity = ImageUtils.compareHistogram(img,disease.getImage());
                if (similarity>=0.4){
                    res.add(new subDisease(disease1,similarity));
                    break;
                }
            }
        }
        return res;
    }

}
