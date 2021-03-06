package by.prohor.service.config;

import by.prohor.dao.config.DaoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by Artsiom Prokharau 06.03.2021
 */

@Configuration
@ComponentScan("by.prohor.service")
@Import(value = {DaoConfiguration.class})
public class ServiceConfiguration {


}
