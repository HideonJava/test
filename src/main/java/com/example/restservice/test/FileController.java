package com.example.restservice.test;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Api(tags = "文件下载+单(多)文件上传")
@ApiSort(3)
@RestController
@RequestMapping(value = "/api/new190/")
public class FileController {

    Logger logger= LoggerFactory.getLogger(FileController.class);

    @ApiOperation(value = "下载测试+无参数版")
    @ApiOperationSupport(order=1)
    @GetMapping(value = "/downloadFile1",produces = "application/octet-stream")
    public void postRequest2(HttpServletRequest request, HttpServletResponse response){
        download("test",response);
    }

    @ApiOperation(value = "下载测试+有参数版")
    @ApiOperationSupport(order=2)
    @GetMapping(value = "/downloadFileAndParam",produces = "application/octet-stream")
    public void postRequest2AndParam(@RequestParam(value = "name") String name, HttpServletRequest request, HttpServletResponse response){
        download(name,response);
    }
    @ApiOperation(value = "下载测试+有参数+请求头版")
    @ApiOperationSupport(order=3)
    @GetMapping(value = "/downloadFileAndParam2",produces = "application/octet-stream")
    public void postRequest3AndParam(@RequestHeader(value = "uud") String uud,@RequestParam(value = "name") String name, HttpServletRequest request, HttpServletResponse response){
        logger.info("header:{}",uud);
        download(name,response);
    }

//    @ApiOperation(value = "文件素材上传Model接口")
//    @PostMapping("/uploadModel")
//    @ResponseBody
    public RestMessage uploadModel(HttpServletRequest request,UploadModel uploadModel){
        System.out.println("model名称："+uploadModel.getName());
        List<MultipartFile> a= Lists.newArrayList();
        a.add(uploadModel.getFile());
        List<Map> uploadFiles= upload(request,a.toArray(new MultipartFile[]{}));
        RestMessage rm=new RestMessage();
        rm.setData(uploadFiles);
        return rm;
    }

//    @ApiOperation(value = "文件素材上传Model接口-path")
//    @PostMapping("/uploadModelpath/{userid}")
//    @ResponseBody
    public RestMessage uploadModelpath(HttpServletRequest request,@PathVariable("userid") String userid,UploadModel uploadModel){
        System.out.println("model名称："+uploadModel.getName()+"userId:"+userid);
        List<MultipartFile> a=Lists.newArrayList();
        a.add(uploadModel.getFile());
        List<Map> uploadFiles= upload(request,a.toArray(new MultipartFile[]{}));
        RestMessage rm=new RestMessage();
        rm.setData(uploadFiles);
        return rm;
    }


    private List<Map> upload(HttpServletRequest request,MultipartFile[] files){
        String realPath=request.getSession().getServletContext().getRealPath("/upload");
        File realFile=new File(realPath);
        if (!realFile.exists()){
            realFile.mkdirs();
        }
        List<Map> uploadFiles= Lists.newArrayList();
        System.out.println("进入图片上传接口:"+files.length +"张");
        for (MultipartFile file : files) {
            File targetFile=new File(realFile,file.getOriginalFilename());
            FileOutputStream fileOutputStream=null;
            InputStream ins=null;
            try{
                fileOutputStream=new FileOutputStream(targetFile);
                int i=-1;
                byte[] bytes=new byte[1024*1024];
                ins=file.getInputStream();
                while ((i=ins.read(bytes))!=-1){
                    fileOutputStream.write(bytes,0,i);
                }
            }catch (IOException e){
            }finally {
                closeQuilty(ins);
                closeQuilty(fileOutputStream);
            }
            Map fileInfo= Maps.newHashMap();
            fileInfo.put("id", UUID.randomUUID().toString());
            fileInfo.put("url",targetFile.getPath());
            fileInfo.put("original_name",targetFile.getName());
            uploadFiles.add(fileInfo);
        }
        return uploadFiles;
    }


    @Order(value = 3)
    @ApiOperation(value = "多文件MultipartFile上传")
    @ApiImplicitParams({@ApiImplicitParam(name = "file[]", value = "文件流对象,接收数组格式", required = true,dataType = "MultipartFile",allowMultiple = true),
            @ApiImplicitParam(name = "title", value = "title", required = true)}
    )
    @RequestMapping(value="/uploadMaterial",method = RequestMethod.POST)
    @ResponseBody
    public RestMessage uploadMaterial(@RequestParam(value="file[]",required = true) MultipartFile[] files,@RequestParam(value = "title") String title, HttpServletRequest request) throws IOException {
        //int mul=1*1024*1024;
        List<Map> uploadFiles= upload(request,files);
        RestMessage rm=new RestMessage();
        rm.setData(uploadFiles);
        return rm;
    }

    @Order(value = 2)
    @ApiOperation(value = "单文件File上传")
    @ApiImplicitParams({@ApiImplicitParam(name = "file", value = "文件流对象,接收数组格式", required = true,dataType = "__File"),
            @ApiImplicitParam(name = "title", value = "title", required = true)}
    )
    @RequestMapping(value="/uploadMaterial2",method = RequestMethod.POST)
    @ResponseBody
    public RestMessage uploadMaterial2(@RequestParam(value="file",required = true) MultipartFile file,@RequestParam(value = "title") String title, HttpServletRequest request) throws IOException {
        //int mul=1*1024*1024;
        List<MultipartFile> a=Lists.newArrayList();
        a.add(file);
        List<Map> uploadFiles= upload(request,a.toArray(new MultipartFile[]{}));
        RestMessage rm=new RestMessage();
        rm.setData(uploadFiles);
        return rm;
    }

    @Order(value = 2)
    @ApiOperation(value = "多文件File上传")
    @ApiImplicitParams({@ApiImplicitParam(name = "file[]", value = "文件流对象,接收数组格式", required = true,dataType = "__File",allowMultiple = true),
            @ApiImplicitParam(name = "title", value = "title", required = true)}
    )
    @RequestMapping(value="/uploadMaterial1",method = RequestMethod.POST)
    @ResponseBody
    public RestMessage uploadMaterial1(@RequestParam(value="file[]",required = true) MultipartFile[] files,@RequestParam(value = "title") String title, HttpServletRequest request) throws IOException {
        //int mul=1*1024*1024;
        List<Map> uploadFiles= upload(request,files);
        RestMessage rm=new RestMessage();
        rm.setData(uploadFiles);
        return rm;
    }

    private void closeQuilty(InputStream ins){
        if (ins!=null){
            try {
                ins.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    private void closeQuilty(OutputStream out){
        if (out!=null){
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void download(String name, HttpServletResponse response){
        String fileName=new Random().nextInt(1000)+".txt";
        try {
            response.setContentType("text/plain;charset=UTF-8;");
            response.addHeader("Content-Disposition", "attachment;FileName=" + fileName);
            ByteArrayOutputStream by=new ByteArrayOutputStream();
            String content="This test Download File Api,犹豫,就会败北"+name;
            by.write(content.getBytes());
            by.writeTo(response.getOutputStream());
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }
    }
}
