package net.smartlo.kafka.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger 설정
 */
@Configuration
public class SwaggerConfig {

  private ApiInfo swaggerInfo() {
    return new ApiInfoBuilder().title("Smartlo Kafka API")
        .description("Kafka API Docs").build();
  }

  /**
   * Swagger 기본 설정
   *
   * @return
   */
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(swaggerInfo())
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.ant("/api/**"))
        .build();
  }
}
