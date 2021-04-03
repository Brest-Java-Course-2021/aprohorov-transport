package by.prohor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Artsiom Prokharau 23.03.2021
 */

@SpringBootApplication
@PropertySource({"classpath:request.properties","classpath:url.properties"})
public class TransportParkApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransportParkApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
