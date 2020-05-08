package com.ljz.diagnostic_system.controller;

import com.ljz.diagnostic_system.common.Annotation.SystemControllerLog;
import com.ljz.diagnostic_system.common.CommonResult;
import com.ljz.diagnostic_system.model.Disease;
import com.ljz.diagnostic_system.model.GetDisease;
import com.ljz.diagnostic_system.service.DiseaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/disease")
public class DiseaseController {
    @Autowired
    private DiseaseService diseaseService;

    @RequestMapping(value = "/addDisease", method = RequestMethod.POST)
    @SystemControllerLog(description = "添加病害信息")
    @ResponseBody
    public CommonResult addDisease(Disease disease) {
        diseaseService.addDisease(disease);
        return CommonResult.success(disease.getId());
    }

    @RequestMapping(value = "/getDisease", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult getDisease(GetDisease getDisease) {
        //System.out.println(page);
        return CommonResult.success(diseaseService.getDisease(getDisease));
    }

    @RequestMapping(value = "/deleteDisease", method = RequestMethod.POST)
    @SystemControllerLog(description = "删除病害信息")
    @ResponseBody
    public CommonResult deleteDisease(String id) {
        return CommonResult.success(diseaseService.deleteDisease(id));
    }

    @RequestMapping(value = "/getInformation", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult getInformation(String id) {
        return CommonResult.success(diseaseService.getInformation(id));
    }

    @RequestMapping(value = "/diseaseUpdate", method = RequestMethod.POST)
    @SystemControllerLog(description = "修改病害信息")
    @ResponseBody
    public CommonResult diseaseUpdate(Disease disease) {
        return CommonResult.success(diseaseService.diseaseUpdate(disease));
    }

    @RequestMapping(value = "/diagnosisDisease", method = RequestMethod.POST)
    @SystemControllerLog(description = "图像查询病害信息")
    @ResponseBody
    public CommonResult diagnosisDisease(Disease disease){
        return CommonResult.success(diseaseService.diagnosisDisease(disease));
    }
}
