package com.inditex.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfig {

  // Valor inyectado para la URL de desarrollo
  @Value("${inditex.openapi.dev-url}")
  private String devUrl;

  // Bean para configurar la documentación de OpenAPI
  @Bean
  public OpenAPI myOpenAPI() {
    
    // Configuración del servidor de desarrollo
    Server devServer = new Server();
    devServer.setUrl(devUrl);
    devServer.setDescription("Canal de desarrollo para pruebas de Inditex");

    // Información de contacto
    Contact contact = new Contact();
    contact.setEmail("luis_1994_2010@hotmail.com");
    contact.setName("Luis Calderon");
    contact.setUrl("https://www.inditex.com");

    // Información general sobre la API
    Info info = new Info()
        .title("Prueba Tecnica para Inditex")
        .version("1.0")
        .contact(contact)
        .description("Esta api tiene un endpoint que es para la prueba tecnica");

    // Esquema de seguridad JWT
    SecurityScheme jwtSecurityScheme = new SecurityScheme()
        .type(SecurityScheme.Type.HTTP)
        .scheme("bearer")
        .bearerFormat("JWT")
        .in(SecurityScheme.In.HEADER)
        .name("Authorization");

    // Requisitos de seguridad
    SecurityRequirement securityItem = new SecurityRequirement().addList("jwt", Arrays.asList("read", "write"));

    // Configuración final de OpenAPI
    return new OpenAPI()
        .info(info)
        .addSecurityItem(securityItem)
        .components(new Components().addSecuritySchemes("jwt", jwtSecurityScheme))
        .servers(List.of(devServer));
  }
}
