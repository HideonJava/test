package com.example.restservice.test;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "返回接口类")
public class RestMessage implements Serializable {
    @ApiModelProperty(value = "是否成功")
    private boolean success = true;
    @ApiModelProperty(value = "返回对象")
    private Object data;
    @ApiModelProperty(value = "错误编号")
    private Integer errCode;
    @ApiModelProperty(value = "错误信息")
    private String message;


    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RestMessage() {
        super();
    }

    public RestMessage(Object data) {
        super();
        this.data = data;
    }

    public RestMessage(boolean success, Object data) {
        super();
        this.success = success;
        this.data = data;
    }
}