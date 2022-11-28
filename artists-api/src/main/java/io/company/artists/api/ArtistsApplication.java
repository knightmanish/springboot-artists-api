package io.company.artists.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Artists API", version = "1.0.0-SNAPSHOT", description = "A simple Artists register service"))
@SecurityScheme(name = "artistssecurity", paramName = "x-api-key", scheme = "basic", type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER)
public class ArtistsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ArtistsApplication.class, args);
    }
}
