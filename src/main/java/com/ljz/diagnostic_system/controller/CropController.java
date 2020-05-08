package com.ljz.diagnostic_system.controller;

import com.ljz.diagnostic_system.common.Annotation.SystemControllerLog;
import com.ljz.diagnostic_system.common.CommonResult;
import com.ljz.diagnostic_system.model.Crop;
import com.ljz.diagnostic_system.model.CropType;
import com.ljz.diagnostic_system.model.GetCrop;
import com.ljz.diagnostic_system.service.CropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("crop")
public class CropController {

    @Autowired
    private CropService cropService;

    @RequestMapping(value = "getTypeList", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult getTypeList() {
        return CommonResult.success(cropService.getTypeList());
    }

    @RequestMapping(value = "addType", method = RequestMethod.POST)
    @SystemControllerLog(description = "添加作物类型")
    @ResponseBody
    public CommonResult addType(CropType cropType) {
        cropService.addType(cropType);
        return CommonResult.success(cropType);
    }

    @RequestMapping(value = "deleteType", method = RequestMethod.POST)
    @SystemControllerLog(description = "删除作物类型")
    @ResponseBody
    public CommonResult deleteType(String id){
        if (cropService.deleteType(id)==0){
            return CommonResult.failed();
        }else return CommonResult.success(null);
    }

    @RequestMapping(value = "getCropList", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult getCropList(GetCrop getCrop){
        return CommonResult.success(cropService.getCropList(getCrop));
    }

    @RequestMapping(value = "addCrop", method = RequestMethod.POST)
    @SystemControllerLog(description = "添加作物信息")
    @ResponseBody
    public CommonResult addCrop(Crop crop){
        cropService.addCrop(crop);
        return CommonResult.success(crop);
    }

    @RequestMapping(value = "deleteCrop", method = RequestMethod.POST)
    @SystemControllerLog(description = "删除作物信息")
    @ResponseBody
    public CommonResult deleteCrop(String id){
        if (cropService.deleteCrop(id)==0){
            return CommonResult.failed();
        }else return CommonResult.success(null);
    }

    @RequestMapping(value = "updateCrop", method = RequestMethod.POST)
    @SystemControllerLog(description = "修改作物信息")
    @ResponseBody
    public CommonResult updateCrop(Crop crop){
        if (cropService.updateCrop(crop)==0){
            return CommonResult.failed();
        }else return CommonResult.success(null);
    }

    @RequestMapping(value = "getParts", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult getParts(){
        return CommonResult.success(cropService.getParts());
    }

}
