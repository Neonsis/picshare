package org.neonsis.picshare.common.annotation.resource;

import org.neonsis.picshare.exception.ConfigException;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.io.InputStream;

@ApplicationScoped
public class ClassPathResourceLoader implements ResourceLoader {

    @Override
    public boolean isSupport(String resourceName) {
        return resourceName.equals("classpath:");
    }

    @Override
    public InputStream getInputStream(String resourceName) throws IOException {
        String classPathResourceName = resourceName.replace("classpath:", "");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader != null) {
            InputStream inputStream = classLoader.getResourceAsStream(classPathResourceName);
            if (inputStream != null) {
                return inputStream;
            }
        }
        throw new ConfigException("Classpath resource not found: " + resourceName);
    }
}
