package com.example.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAiConfig {

    @Bean
    public OpenAPI myOpenApi() {

        Server server = new Server();
        server.setUrl("http://localhost:2525/");
        server.setDescription("It is my production server");

        Contact contact = new Contact();
        contact.setUrl("www.prodigitalcourse.org");
        contact.setEmail("mirkomilablayev777@gmail.com");
        contact.setName("Mirkomil Ablayev");

        License license = new License().name("My Licence").url("www.licence.uz");
        Info info = new Info()
                .title("Tutorial api management")
                .version("1.0.0")
                .contact(contact)
                .license(license)
                .description("this api is a description!");

        return new OpenAPI().info(info).servers(List.of(server));


    }

}
