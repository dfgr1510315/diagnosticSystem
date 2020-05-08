package com.ljz.diagnostic_system.service;

import com.github.pagehelper.PageInfo;
import com.ljz.diagnostic_system.model.Crop;
import com.ljz.diagnostic_system.model.CropType;
import com.ljz.diagnostic_system.model.GetCrop;

import java.util.List;

/**
 * 获取农作物信息
 */
public interface CropService {

    /**
     * 获取农作物类型
     * @return 农作物类型名和id
     */
    List<CropType> getTypeList();

    void addType(CropType cropType);

    int deleteType(String id);

    PageInfo getCropList(GetCrop getCrop);

    void addCrop(Crop crop);

    int deleteCrop(String id);

    int updateCrop(Crop crop);

    List<CropType> getParts();
}
