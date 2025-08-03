package com.jack.asignment.general.data.query.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring Boot Interface Specification")
                        .version("1.0.0")  // 版本
                        .description("This is a demo of Spring Boot integrating Swagger")
                        .contact(new Contact()  // 联系人信息
                                .name("Jack")
                                .email("zhangqingxiang1314@163.com"))
                        .license(new License()  // 许可证信息
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }
}