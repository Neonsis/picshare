package org.neonsis.picshare.common.model;

import org.neonsis.picshare.exception.ApplicationException;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class URLImageResource extends AbstractMimeTypeImageResource {

    private final String url;
    private final URLConnection urlConnection;

    public URLImageResource(String url) {
        this.url = url;
        try {
            this.urlConnection = new URL(url).openConnection();
        } catch (IOException ex) {
            throw new ApplicationException("Can't open connection to url: " + url, ex);
        }
    }

    @Override
    protected String getContentType() {
        return urlConnection.getContentType();
    }

    @Override
    protected void copyContent() throws IOException {
        Files.copy(urlConnection.getInputStream(), getTempPath(), REPLACE_EXISTING);
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", getClass().getSimpleName(), url);
    }

    @Override
    protected void deleteTempResources() {
        //do nothing
    }
}
