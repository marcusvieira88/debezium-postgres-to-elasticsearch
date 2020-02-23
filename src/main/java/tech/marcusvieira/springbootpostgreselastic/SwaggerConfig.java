package tech.marcusvieira.springbootpostgreselastic;

import java.util.ArrayList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    private static final Contact DEFAULT_CONTACT = new Contact("Marcus Vieira", "https://marcusvieira.tech",
        "marcusvinicius.vieira88@gmail.com");
    private static final ApiInfo DEFAULT_API_INFO = new ApiInfo("Spring Boot Api Documentation",
        "Spring Boot Api Documentation", "1.0", "urn:tos", DEFAULT_CONTACT, "Apache 2.0",
        "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList());

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(DEFAULT_API_INFO);
    }
}
