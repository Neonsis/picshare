package org.neonsis.picshare.service;

import org.neonsis.picshare.exception.ObjectNotFoundException;
import org.neonsis.picshare.model.*;
import org.neonsis.picshare.model.domain.Photo;
import org.neonsis.picshare.model.domain.Profile;

import java.util.List;

public interface PhotoService {

    List<Photo> findProfilePhotos(Long profileId, Pageable pageable);

    List<Photo> findPopularPhotos(SortMode sortMode, Pageable pageable);

    long countAllPhotos();

    String viewLargePhoto(Long photoId) throws ObjectNotFoundException;

    OriginalImage downloadOriginalImage(Long photoId) throws ObjectNotFoundException;

    void uploadNewPhoto(Profile currentProfile, ImageResource imageResource, AsyncOperation<Photo> asyncOperation);
}
