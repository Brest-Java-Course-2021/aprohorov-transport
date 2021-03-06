package by.prohor.dao.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by Artsiom Prokharau 06.03.2021
 */

@Configuration
@ComponentScan("by.prohor.dao")
@ImportResource({"classpath*:config-db.xml", "classpath*:test-dao.xml"})
@PropertySource("classpath:request.properties")
public class DaoConfiguration {

}
