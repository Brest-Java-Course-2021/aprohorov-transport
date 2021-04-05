package by.prohor.webapp;

import by.prohor.webapp.config_rest.RestConnectConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Created by Artsiom Prokharau 23.03.2021
 */

@SpringBootApplication
@Import(RestConnectConfiguration.class)
public class TransportParkApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransportParkApplication.class, args);
    }
}
