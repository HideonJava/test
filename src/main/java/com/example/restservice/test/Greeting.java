package com.example.restservice.test;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="Greeting实体类",description="Greeting实体类")
public class Greeting {

    @ApiModelProperty(value="主键id",example = "1")
    private final long id;

    @ApiModelProperty(value="内容",example = "莫西莫西",required = true,dataType = "String")
    private final String content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
