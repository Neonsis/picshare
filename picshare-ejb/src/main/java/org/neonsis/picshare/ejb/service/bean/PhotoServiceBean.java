package org.neonsis.picshare.ejb.service.bean;

import org.neonsis.picshare.ejb.repository.PhotoRepository;
import org.neonsis.picshare.ejb.repository.ProfileRepository;
import org.neonsis.picshare.exception.ObjectNotFoundException;
import org.neonsis.picshare.exception.ValidationException;
import org.neonsis.picshare.model.*;
import org.neonsis.picshare.model.domain.Photo;
import org.neonsis.picshare.model.domain.Profile;
import org.neonsis.picshare.service.PhotoService;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Stateless
@LocalBean
@Local(PhotoService.class)
public class PhotoServiceBean implements PhotoService {

    @Inject
    private PhotoRepository photoRepository;

    @Inject
    private ProfileRepository profileRepository;

    @Resource
    private SessionContext sessionContext;


    @Override
    public List<Photo> findProfilePhotos(Long profileId, Pageable pageable) {
        return photoRepository.findProfilePhotosLatestFirst(profileId, pageable.getOffset(), pageable.getLimit());
    }

    @Override
    public List<Photo> findPopularPhotos(SortMode sortMode, Pageable pageable) {
        switch (sortMode) {
            case POPULAR_AUTHOR:
                return photoRepository.findAllOrderByAuthorRatingDesc(pageable.getOffset(), pageable.getLimit());
            case POPULAR_PHOTO:
                return photoRepository.findAllOrderByViewsDesc(pageable.getOffset(), pageable.getLimit());
            default:
                throw new ValidationException("Unsupported sort mode: " + sortMode);
        }
    }

    @Override
    public long countAllPhotos() {
        return photoRepository.countAll();
    }

    @Override
    public String viewLargePhoto(Long photoId) throws ObjectNotFoundException {
        Photo photo = getPhoto(photoId);
        photo.setViews(photo.getViews() + 1);
        photoRepository.update(photo);
        return photo.getLargeUrl();
    }

    @Override
    public OriginalImage downloadOriginalImage(Long photoId) throws ObjectNotFoundException {
        Photo photo = getPhoto(photoId);
        photo.setDownloads(photo.getDownloads() + 1);
        photoRepository.update(photo);

        throw new UnsupportedOperationException("Not impl yet");
    }

    public Photo getPhoto(Long photoId) throws ObjectNotFoundException {
        Optional<Photo> photoOptional = photoRepository.findById(photoId);
        if (!photoOptional.isPresent()) {
            throw new ObjectNotFoundException("Photo not found by id: " + photoId);
        }
        return photoOptional.get();
    }

    @Override
    @Asynchronous
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void uploadNewPhoto(Profile currentProfile, ImageResource imageResource, AsyncOperation<Photo> asyncOperation) {
        try {
            Photo photo = null; // TODO
            asyncOperation.onSuccess(photo);
        } catch (Throwable throwable) {
            sessionContext.setRollbackOnly();
            asyncOperation.onFailed(throwable);
        }
    }
}
