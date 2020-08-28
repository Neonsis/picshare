package org.neonsis.picshare.ejb.service.bean;

import org.neonsis.picshare.common.annotation.cdi.Property;
import org.neonsis.picshare.ejb.repository.ProfileRepository;
import org.neonsis.picshare.exception.ObjectNotFoundException;
import org.neonsis.picshare.model.AsyncOperation;
import org.neonsis.picshare.model.ImageResource;
import org.neonsis.picshare.model.domain.Profile;
import org.neonsis.picshare.service.ProfileService;

import javax.ejb.*;
import javax.inject.Inject;
import java.util.Optional;
import java.util.logging.Logger;

@Stateless
@Local(ProfileService.class)
public class ProfileServiceBean implements ProfileService {

    @Inject
    private Logger logger;

    @Inject
    private ProfileRepository profileRepository;

    @Inject
    @Property("picshare.profile.avatar.placeholder.url")
    private String avatarPlaceholderUrl;

    @Override
    public Profile findById(Long id) throws ObjectNotFoundException {
        Optional<Profile> profileOptional = profileRepository.findById(id);
        if (!profileOptional.isPresent()) {
            throw new ObjectNotFoundException(String.format("Profile not found by id: %s", id));
        }
        return profileOptional.get();
    }

    @Override
    public Profile findByUid(String uid) throws ObjectNotFoundException {
        Optional<Profile> profileOptional = profileRepository.findByUid(uid);
        if (!profileOptional.isPresent()) {
            throw new ObjectNotFoundException(String.format("Profile not found by uid: %s", uid));
        }
        return profileOptional.get();
    }

    @Override
    public Optional<Profile> findByEmail(String email) {
        return profileRepository.findByEmail(email);
    }

    @Override
    public void signUp(Profile profile, boolean uploadProfileAvatar) {
        // TODO
    }

    @Override
    public void translitSocialProfile(Profile profile) {
        // TODO
    }

    @Override
    public void update(Profile profile) {
        profileRepository.update(profile);
    }

    @Override
    @Asynchronous
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void uploadNewAvatar(Profile currentProfile, ImageResource imageResource, AsyncOperation<Profile> asyncOperation) {
        try {
            uploadNewAvatar(currentProfile, imageResource);
            asyncOperation.onSuccess(currentProfile);
        } catch (Throwable throwable) {
            setAvatarPlaceHolder(currentProfile);
            asyncOperation.onFailed(throwable);
        }
    }

    public void uploadNewAvatar(Profile currentProfile, ImageResource imageResource) {
        // TODO
    }

    public void setAvatarPlaceHolder(Profile currentProfile) {
        if (currentProfile.getAvatarUrl() == null) {
            currentProfile.setAvatarUrl(avatarPlaceholderUrl);
            profileRepository.update(currentProfile);
        }
    }
}
