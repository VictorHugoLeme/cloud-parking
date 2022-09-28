package dev.victorhleme.parking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

@Component
public class SwaggerConfig {

    @Bean
    public Docket getDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("dev.victorhleme.parking"))
            .build()
            .apiInfo(metaData())
            .securityContexts(actuatorSecurityContext())
            .securitySchemes(basicAuthScheme());
    }


    private List<SecurityContext> actuatorSecurityContext() {
        return List.of(SecurityContext.builder()
            .securityReferences(List.of(basicAuthReference()))
            .build());
    }

    private SecurityReference basicAuthReference() {
        return new SecurityReference("basicAuth", new AuthorizationScope[0]);
    }

    private List<SecurityScheme> basicAuthScheme() {
        return List.of(new BasicAuth("basicAuth"));
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
            .title("Cloud Parking API")
            .description("API for a parking lot")
            .version("1.0.0")
            .license("Apache License Version 2.0")
            .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
            .build();
    }

}
