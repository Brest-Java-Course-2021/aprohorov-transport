package by.prohor.webapp.config_rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Artsiom Prokharau 05.04.2021
 */

@Configuration
@ComponentScan({"by.prohor.service", "by.prohor.dao"})
@PropertySource({"classpath:request.properties", "classpath:url.properties"})
public class RestConnectConfiguration {


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
