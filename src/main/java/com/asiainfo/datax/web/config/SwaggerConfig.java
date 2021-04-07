package com.asiainfo.datax.web.config;

import com.google.common.collect.Sets;
import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Value("${spring.application.name}")
	private String appName;
    @Value("${spring.application.version}")
	private String appVersion;

    /**
     * 设置监控路径,默认监控
     * @return
     */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
		        .produces(Sets.newHashSet("application/json"))
                .consumes(Sets.newHashSet("application/json"))
                .apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors
						.basePackage("com.asiainfo.datax.web.controller"))
				.paths(PathSelectors.any())
				.build();
	}

    /**
     * ApiInfo
     * @return
     */
	 private ApiInfo apiInfo()
	    {
	        return new ApiInfoBuilder()
	                .title(appName)
	                .version(appVersion)
	                .build();
	    }
}
