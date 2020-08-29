package org.neonsis.picshare.rest.config;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import static org.neonsis.picshare.rest.Constants.CURRENT_VERSION;

@ApplicationPath(CURRENT_VERSION)
public class ApplicationConfig extends Application {
}
