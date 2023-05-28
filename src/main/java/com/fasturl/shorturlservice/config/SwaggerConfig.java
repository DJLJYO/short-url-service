package com.fasturl.shorturlservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger配置类
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

    @Value("${Swagger.title}")
    private String title;

    @Value("${Swagger.description}")
    private String description;

    /**
     * 创建指定要包含在Swagger文档中的API接口
     *
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                // api选择的构建器 要完成api选择器的构建，需要调用api选择器的构建方法
                .select()
                // 扫描包中的api
                .apis(RequestHandlerSelectors.basePackage("com.fasturl.shorturlservice.controller"))
                // 配置需要生成的api
                .paths(PathSelectors.any())
                .build()
                // api信息
                .apiInfo(apiInfo());
    }

    /**
     * 文档的api信息
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // api标题
                .title(title)
                // api文档描述
                .description(description)
                .version("1.1")
                .build();
    }
}
