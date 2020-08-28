package org.neonsis.picshare.ejb.service.impl;

import org.neonsis.picshare.common.annotation.cdi.Property;
import org.neonsis.picshare.common.config.ImageCategory;
import org.neonsis.picshare.ejb.service.FileNameGeneratorService;
import org.neonsis.picshare.ejb.service.ImageStorageService;
import org.neonsis.picshare.exception.ApplicationException;
import org.neonsis.picshare.model.OriginalImage;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@ApplicationScoped
public class LocalPathImageStorageService implements ImageStorageService {

    @Inject
    private Logger logger;

    @Inject
    @Property("picshare.storage.root.dir")
    private String storageRoot;

    @Inject
    @Property("picshare.media.absolute.root")
    private String mediaRoot;

    @Inject
    private FileNameGeneratorService fileNameGeneratorService;

    @Override
    public String saveProtectedImage(Path path) {
        String fileName = fileNameGeneratorService.generateUniqueFileName();
        Path destinationPath = Paths.get(storageRoot, fileName);
        saveImage(path, destinationPath);
        return fileName;
    }

    @Override
    public String savePublicImage(ImageCategory imageCategory, Path path) {
        String fileName = fileNameGeneratorService.generateUniqueFileName();
        Path destinationPath = Paths.get(mediaRoot, imageCategory.getRelativeRoot(), fileName);
        saveImage(path, destinationPath);
        return "/" + imageCategory.getRelativeRoot() + fileName;
    }

    private void saveImage(Path sourcePath, Path destinationPath) {
        try {
            Files.move(sourcePath, destinationPath, REPLACE_EXISTING);
        } catch (IOException | RuntimeException ex) {
            logger.log(Level.WARNING, String.format("Move failed from %s to %s. Try to copy...", sourcePath, destinationPath), ex);
            try {
                Files.copy(sourcePath, destinationPath, REPLACE_EXISTING);
            } catch (IOException e) {
                ApplicationException applicationException = new ApplicationException("Can't save image: " + destinationPath, e);
                applicationException.addSuppressed(ex);
                throw applicationException;
            }
        }
        logger.log(Level.INFO, "Saved image: {0}", destinationPath);
    }

    @Override
    public void deletePublicImage(String url) {
        Path destinationPath = Paths.get(mediaRoot, url.substring(1));
        try {
            Files.deleteIfExists(destinationPath);
        } catch (IOException | RuntimeException e) {
            logger.log(Level.SEVERE, "Delete public image failed: " + destinationPath, e);
        }
    }

    @Override
    public OriginalImage getOriginalImage(String originalUrl) {
        Path originalPath = Paths.get(storageRoot, originalUrl);
        try {
            return new OriginalImage(
                    Files.newInputStream(originalPath),
                    Files.size(originalPath),
                    originalPath.getFileName().toString());
        } catch (IOException ex) {
            throw new ApplicationException(String.format("Can't get access to original image: %s", originalPath), ex);
        }
    }
}
