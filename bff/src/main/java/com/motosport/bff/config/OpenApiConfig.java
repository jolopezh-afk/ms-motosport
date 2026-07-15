package com.motosport.bff.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI motosportOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("BikeSport BFF API")
                        .description("Backend For Frontend para BikeSport")
                        .version("v1")
                        .contact(new Contact()
                                .name("BikeSport Team")
                                .email("dev@motosport.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}