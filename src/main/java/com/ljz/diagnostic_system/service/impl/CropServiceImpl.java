package com.ljz.diagnostic_system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ljz.diagnostic_system.dao.CropDao;
import com.ljz.diagnostic_system.model.Crop;
import com.ljz.diagnostic_system.model.CropType;
import com.ljz.diagnostic_system.model.GetCrop;
import com.ljz.diagnostic_system.service.CropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CropServiceImpl implements CropService {

    @Autowired
    private CropDao cropDao;

    @Override
    @Cacheable(value = "type",key = "'typeList'")
    public List<CropType> getTypeList() {
        return cropDao.getTypeList();
    }

    @Override
    @CacheEvict(value = "type",key = "'typeList'")
    public void addType(CropType cropType) {
        cropDao.addType(cropType);
    }

    @Override
    @CacheEvict(value = "type",key = "'typeList'")
    public int deleteType(String id) {
        return cropDao.deleteType(id);
    }

    @Override
    @Cacheable(value = "crop",key = "'cropList'+#getCrop.getPage()",condition = "#getCrop.getName()==''&&#getCrop.getType()==''&&#getCrop.getPageSize()==10")
    public PageInfo getCropList(GetCrop getCrop) {
        PageHelper.startPage(getCrop.getPage(),getCrop.getPageSize());
        List<Crop> cropList = cropDao.getCropList(getCrop);
        return new PageInfo<>(cropList);
    }

    @Override
    @CacheEvict(value = "crop",allEntries = true)
    public void addCrop(Crop crop) {
        cropDao.addCrop(crop);
    }

    @Override
    @CacheEvict(value = "crop",allEntries = true)
    public int deleteCrop(String id) {
        return cropDao.deleteCrop(id);
    }

    @Override
    @CacheEvict(value = "crop",allEntries = true)
    public int updateCrop(Crop crop) {
        return cropDao.updateCrop(crop);
    }

    @Override
    @Cacheable(value = "parts",key = "'partsList'")
    public List<CropType> getParts() {
        return cropDao.getParts();
    }
}
