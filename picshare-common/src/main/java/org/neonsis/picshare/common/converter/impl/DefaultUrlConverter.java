package org.neonsis.picshare.common.converter.impl;

import org.neonsis.picshare.common.annotation.cdi.Property;
import org.neonsis.picshare.common.converter.UrlConverter;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;

public class DefaultUrlConverter implements UrlConverter {

    @Inject
    @Property("picshare.host")
    private String host;

    @Override
    public String convert(String url) {
        try {
            if (new URI(url).isAbsolute()) {
                return url;
            }
        } catch (URISyntaxException e) {
            // do nothing. Url is relative
        }
        return host + url;
    }
}
