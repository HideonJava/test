package com.example.restservice.test;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.multipart.MultipartFile;

public class UploadModel {
    private String name;

    @ApiModelProperty(dataType = "",value = "上传文件")
    private MultipartFile file;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
