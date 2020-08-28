package org.neonsis.picshare.ejb.service;

import org.neonsis.picshare.common.config.ImageCategory;
import org.neonsis.picshare.model.OriginalImage;

import java.nio.file.Path;

public interface ImageStorageService {

    String saveProtectedImage(Path path);

    String savePublicImage(ImageCategory imageCategory, Path path);

    void deletePublicImage(String url);

    OriginalImage getOriginalImage(String originalUrl);
}
