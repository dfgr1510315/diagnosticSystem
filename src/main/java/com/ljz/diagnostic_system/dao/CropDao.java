package com.ljz.diagnostic_system.dao;

import com.ljz.diagnostic_system.model.Crop;
import com.ljz.diagnostic_system.model.CropType;
import com.ljz.diagnostic_system.model.GetCrop;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CropDao {
    List<CropType> getTypeList();

    void addType(CropType cropType);

    int deleteType(String id);

    List<Crop> getCropList(GetCrop getCrop);

    void addCrop(Crop crop);

    int deleteCrop(String id);

    int updateCrop(Crop crop);

    List<CropType> getParts();
}
