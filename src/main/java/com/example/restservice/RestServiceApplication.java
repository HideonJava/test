package com.example.restservice;

import com.example.restservice.restTemplate.Quote;
import com.example.restservice.storage.StorageProperties;
import com.example.restservice.storage.StorageService;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
//@ComponentScan(value = "com.example.restservice.*")
@EnableSwagger2
@EnableKnife4j
public class RestServiceApplication {

    private static final Logger log = LoggerFactory.getLogger(RestServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RestServiceApplication.class, args);
	}

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        return args -> {
            Quote quote = restTemplate.getForObject(
                    "https://gturnquist-quoters.cfapps.io/api/random", Quote.class);
            log.info(quote.toString());
        };
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.deleteAll();
            storageService.init();
        };
    }

}
