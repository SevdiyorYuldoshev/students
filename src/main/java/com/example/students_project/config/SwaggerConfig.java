package com.example.students_project.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SecurityScheme(name = "Authorization",
        in = SecuritySchemeIn.HEADER,
        type = SecuritySchemeType.APIKEY)
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Sevdiyor",
                        url = "https://t.me/Sevdiyor_Yoldoshev"
                )
        )
)
public class SwaggerConfig {
}
