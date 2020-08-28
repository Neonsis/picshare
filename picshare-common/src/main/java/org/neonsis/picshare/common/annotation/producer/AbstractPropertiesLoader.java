package org.neonsis.picshare.common.annotation.producer;

import org.neonsis.picshare.common.annotation.resource.ResourceLoaderManager;

import javax.enterprise.inject.Vetoed;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@Vetoed
abstract class AbstractPropertiesLoader {

    @Inject
    protected Logger logger;

    @Inject
    protected ResourceLoaderManager resourceLoaderManager;

    protected void loadProperties(Properties properties, String resourceName) {
        try {
            try (InputStream in = resourceLoaderManager.getResourceInputStream(resourceName)) {
                properties.load(in);
            }
            logger.log(Level.INFO, "Successful loaded properties from: " + resourceName);
        } catch (IOException | RuntimeException ex) {
            logger.log(Level.WARNING, "Can't load properties from: " + resourceName, ex);
        }
    }
}
