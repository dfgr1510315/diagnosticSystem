package com.ljz.diagnostic_system.controller;

import com.ljz.diagnostic_system.common.CommonResult;
import com.ljz.diagnostic_system.common.Utils.FileUtils;
import com.ljz.diagnostic_system.common.Utils.MD5Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/upload")
public class UploadController {

    @RequestMapping(value = "/uploadImage/{imageType}", method = RequestMethod.POST)
    @ResponseBody
    public List<CommonResult> uploadImage(@RequestParam("file")MultipartFile[] files, @PathVariable String imageType){
        List<CommonResult> commonResults = new ArrayList<>();
        if (files==null||files.length==0)  commonResults.add(CommonResult.validateFailed());
        else{
            for (MultipartFile file : files){
                if (file.getSize()  > 5242880){
                    commonResults.add(CommonResult.failed("图片 "+file.getOriginalFilename()+" 大小不能超过5M"));
                }
                else{
                    //判断上传文件格式
                    String fileType = file.getContentType();
                    if (Objects.equals(fileType, "image/jpg") || Objects.equals(fileType, "image/png") || Objects.equals(fileType, "image/jpeg")) {
                        // 要上传的目标文件存放的绝对路径
                        //用src为保存绝对路径不能改名只能用原名，不用原名会导致ajax上传图片后在前端显示时出现404错误-->没有加@ResponseBody
//                      String localPath="F:\\IDEAProject\\imageupload\\src\\main\\resources\\static\\img";
                        //final String localPath=" D:\\IntelliJIDEA2018.2\\javaweb\\diagnosticSystem\\src\\main\\resources\\static\\files\\image\\"+imageType;
                        final String localPath="/usr/local/tomcat/webapps/diagnostic_system/WEB-INF/classes/static/files/image/"+imageType;
                        //上传后保存的文件名(需要防止图片重名导致的文件覆盖)
                        //获取文件名
                        String fileName = file.getOriginalFilename();
                        //获取文件后缀名
                        assert fileName != null;
                        String suffixName = fileName.substring(fileName.lastIndexOf("."));
                        //重新生成文件名
                        //fileName = UUID.randomUUID()+suffixName;
                        fileName = MD5Utils.getFileMD5String(file)+suffixName;
                        if (FileUtils.upload(file, localPath, fileName)) {
                            //文件存放的相对路径(一般存放在数据库用于img标签的src)
                            //String relativePath="files/image/"+imageType+fileName;
                            //前端根据是否存在该字段来判断上传是否成功
                            commonResults.add(CommonResult.success(fileName));
                        }
                        else{
                            commonResults.add(CommonResult.failed("图片 "+file.getOriginalFilename()+" 上传失败"));
                        }
                    }
                    else{
                        commonResults.add(CommonResult.failed("图片 "+file.getOriginalFilename()+" 格式不正确"));
                    }
                }
            }
        }
        return commonResults;
    }
}
