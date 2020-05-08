package com.ljz.diagnostic_system.controller;

import com.ljz.diagnostic_system.common.Annotation.SystemControllerLog;
import com.ljz.diagnostic_system.common.CommonResult;
import com.ljz.diagnostic_system.model.UserProfile;
import com.ljz.diagnostic_system.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/user")
public class UmsAdminController {
    @Autowired
    private UmsAdminService umsAdminService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @SystemControllerLog(description = "登录系统")
    @ResponseBody
    public CommonResult login(UserProfile userProfile,HttpServletRequest request) {
        userProfile = umsAdminService.login(userProfile.getEmail(), userProfile.getPassword());
        if (userProfile == null) {
            return CommonResult.validateFailed();
        }
     /*   Map<String, String> map = new HashMap<>();
        map.put("token", userProfile.getToken());
        map.put("username",userProfile.getUsername());*/
        request.setAttribute("id",userProfile.getId());
        return CommonResult.success(userProfile);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    //@SystemControllerLog(description = "新用户注册")
    @ResponseBody
    public CommonResult register(UserProfile userProfile) {
        int res  = umsAdminService.register(userProfile);
        if (res == 0) {
            return CommonResult.failed("注册失败");
        }
        return CommonResult.success("注册成功");
    }

    @RequestMapping(value = "/newUsername", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult newUsername(HttpServletRequest request, String newName){
        if (umsAdminService.newUsername((String) request.getAttribute("id"),newName)==0){
            return CommonResult.failed();
        }else return CommonResult.success();
    }

    @RequestMapping(value = "/newPassword", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult newPassword(HttpServletRequest request, String oldPassword,String password){
        String userId = (String) request.getAttribute("id");
        final UserProfile userProfile = umsAdminService.loadUserById(userId);
        if (!userProfile.getPassword().equals(oldPassword)) return CommonResult.validateFailed("原密码错误");
        if (umsAdminService.newPassword((String) request.getAttribute("id"),password)==0){
            return CommonResult.failed();
        }else return CommonResult.success();
    }
}
