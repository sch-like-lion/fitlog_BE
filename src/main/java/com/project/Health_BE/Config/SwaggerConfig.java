package com.project.Health_BE.Config;

// [중요] annotations 패키지가 아니라 models 패키지를 사용해야 합니다.
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info; // ProcessHandle.Info (X) -> Info (O)
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.PropertyCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        // JWT 인증 스킴 설정
        String jwt = "JWT";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt);
        Components components = new Components().addSecuritySchemes(jwt, new SecurityScheme()
                .name(jwt)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
        );

        return new OpenAPI()
                .components(components)
                .info(apiInfo())
                .addSecurityItem(securityRequirement);
    }
    // API 기본 정보 설정
    private Info apiInfo() {
        return new Info()
                .title("Health_API")
                .description("Health 백엔드 API 문서입니다.")
                .version("1.0.0");
    }

    //API 문서에서 Null 가능 표시를 강제히 비활성화
    @Bean
    public PropertyCustomizer disableNullableCustomizer() {
        return (schema, type) -> {
            schema.setNullable(false);
            return schema;
        };
    }
}