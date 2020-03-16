package com.example.restservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 *  Swagger2 配置类
 * @author vi
 * @since 2019/3/6 8:31 PM
 */
@Configuration
public class Swagger2Config {

    @Bean(value = "test")
    @Order(value = 1)
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
//                .enable(false) // 是否启用swagger
                .apiInfo(apiInfo())
                .groupName("测试接口")
                .securitySchemes(createSecuritySchemeList())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.restservice.test"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Springboot2.x-swagger2")   //标题
                .description("Restful-API-Doc") //描述
                .termsOfServiceUrl("http://localhost:8080/") //这里配置的是服务网站
                .contact(new Contact("香的一批", "https://www.baidu.com", "xxx@gmail.com")) // 三个参数依次是姓名，个人网站，邮箱
                .version("1.0") //版本
                .build();
    }


    private List<SecurityScheme> createSecuritySchemeList(){
        List<SecurityScheme> list = new ArrayList<>();
        list.add(new ApiKey("access_token", "access_token鉴权值", "header"));
//        list.add(new ApiKey("access_token1", "access_token1鉴权值", "query"));
        return list;
    }

}
