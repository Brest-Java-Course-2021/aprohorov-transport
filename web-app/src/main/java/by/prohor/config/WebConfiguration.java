package by.prohor.config;

import by.prohor.service.config.ServiceConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by Artsiom Prokharau 09.03.2021
 */

@Configuration
@ComponentScan("by.prohor.controller")
@Import({ThymeleafConfiguration.class, ServiceConfiguration.class})
@EnableWebMvc
public class WebConfiguration{

}
