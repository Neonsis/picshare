package org.neonsis.picshare.ejb.service;

import org.neonsis.picshare.common.config.ImageCategory;

import java.nio.file.Path;

public interface ImageResizerService {

    void resize(Path sourcePath, Path destinationPath, ImageCategory imageCategory);
}
