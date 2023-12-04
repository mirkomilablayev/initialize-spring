package com.example;

import com.example.exceptions.GenericException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;

import java.io.*;
import java.util.Properties;

@SpringBootApplication
@Slf4j
public class Application {

    public static void main(String[] args) {
        protectDDL();
        SpringApplication.run(Application.class, args);
    }

    private static void protectDDL() {
        String path = "src/main/resources/application.yml";
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.lines()
                    .filter(line -> line.contains("ddl-auto") && (line.contains("create") || line.contains("create-drop")))
                    .findFirst()
                    .ifPresent(line -> {
                        log.error("ddl is now create or create-drop, it is dangerous for your database!");
                        System.exit(1);
                    });
        } catch (Exception e) {
            throw new GenericException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
