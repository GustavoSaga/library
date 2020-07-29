package br.com.library.docs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket apiDoc () {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.library.endpoint"))
                .paths(regex("/v1.*"))
                .build()
                .globalOperationParameters(Collections.singletonList(new ParameterBuilder()
                        .name("Authorization")
                        .description("Bearer YOUR_TOKEN")
                        .modelRef(new ModelRef("String"))
                        .parameterType("header")
                        .required(true)
                        .build()))
                .apiInfo(metaData());
    }

    private ApiInfo metaData () {
        return new ApiInfoBuilder()
                .title("Library")
                .description("Simple CRUD for a library")
                .version("1.0")
                .contact(new Contact("Gustavo Souza", "https://github.com/GustavoSaga", "gustavo_saga@live.com"))
                .license("Apache license")
                .licenseUrl("License URL")
                .build();
    }
}
