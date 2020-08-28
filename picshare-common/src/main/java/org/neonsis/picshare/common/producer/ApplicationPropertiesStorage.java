package org.neonsis.picshare.common.producer;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;

@ApplicationScoped
public class ApplicationPropertiesStorage extends AbstractPropertiesLoader {

    private static final String APPLICATION_CONFIG_FILE = "PICSHARE_CONFIG_FILE";

    private static final String APPLICATION_CONFIG_PROPERTY_PREFIX = "picshare.";

    private final Properties applicationProperties = new Properties();

    Properties getApplicationProperties() {
        return applicationProperties;
    }

    @PostConstruct
    private void postConstruct() {
        loadProperties(applicationProperties, "classpath:application.properties");
        overrideApplicationProperties(applicationProperties, System.getenv(), "System environment");
        overrideApplicationProperties(applicationProperties, System.getProperties(), "System properties");
        logger.log(Level.INFO, "Application properties loaded successfully");
    }

    private void overrideApplicationProperties(Properties applicationProperties, Map<?, ?> map, String description) {
        String configFilePath = null;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            String key = String.valueOf(entry.getKey());
            if (key.startsWith(APPLICATION_CONFIG_PROPERTY_PREFIX)) {
                applicationProperties.setProperty(key, String.valueOf(entry.getValue()));
                logger.log(Level.INFO, "Overridden application property {0}, defined in the {1}", new String[]{key, description});
            } else if (APPLICATION_CONFIG_FILE.equals(key)) {
                configFilePath = String.valueOf(entry.getValue());
            }
        }
        if (configFilePath != null && Files.exists(Paths.get(configFilePath))) {
            loadProperties(applicationProperties, configFilePath);
            logger.log(Level.INFO, "Overridden application property from file {0}, defined in the {1}", new String[]{configFilePath, description});
        }
    }
}
