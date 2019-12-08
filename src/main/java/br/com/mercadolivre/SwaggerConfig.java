package br.com.mercadolivre;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.mercadolivre.controller"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo(){
        return new ApiInfo(
                "Simian REST API",
                "A REST API to simian verification",
                "API Simian",
                "Terms of service",
                contact(),
                "License of API",
                "https://www.gnu.org/licenses/gpl-3.0.pt-br.html",
                Collections.emptyList());
    }

    private Contact contact(){
        return new Contact(
                "Vinicius Duarte",
                "https://www.linkedin.com/in/vinidual/",
                "vinidual@gmail.com");
    }

}
