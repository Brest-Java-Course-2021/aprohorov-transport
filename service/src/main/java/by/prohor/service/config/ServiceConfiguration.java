package by.prohor.service.config;

import by.prohor.dao.DaoApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by Artsiom Prokharau 04.03.2021
 */

@Configuration
@Import(DaoApplication.class)
@ComponentScan("by.prohor.service")
public class ServiceConfiguration {
}
