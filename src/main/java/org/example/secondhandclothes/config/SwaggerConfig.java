package org.example.secondhandclothes.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
    name = "auth",
    scheme = "bearer",
    type = SecuritySchemeType.HTTP,
    in = SecuritySchemeIn.HEADER)
public class SwaggerConfig {

  @Bean
  public OpenAPI myOpenAPI() {
    Info info = new Info()
            .title("Second Hand Clothes API")
            .version("1.0")
            .description("This API exposes endpoints to manage second hand clothes.");

    return new OpenAPI().info(info);
  }
}
