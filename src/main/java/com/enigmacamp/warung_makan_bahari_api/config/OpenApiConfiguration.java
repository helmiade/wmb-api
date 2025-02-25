package com.enigmacamp.warung_makan_bahari_api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

    @Configuration
    @OpenAPIDefinition(info = @Info(
            title = "Warung Makan Bahari",
            version = "1.0",
            contact = @Contact(
                    name = "enigmacamp",
                    url = "https://enigmacamp.com")))
    @SecurityScheme(
            name = "Bearer Authenticator",
            type = SecuritySchemeType.HTTP,
            bearerFormat = "JWT",
            scheme = "bearer"
    )
    public class OpenApiConfiguration {

    }


