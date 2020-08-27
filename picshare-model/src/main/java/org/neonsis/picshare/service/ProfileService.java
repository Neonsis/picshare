package org.neonsis.picshare.service;

import org.neonsis.picshare.exception.ObjectNotFoundException;
import org.neonsis.picshare.model.AsyncOperation;
import org.neonsis.picshare.model.ImageResource;
import org.neonsis.picshare.model.domain.Profile;

import java.util.Optional;

public interface ProfileService {

    Profile findById(Long id) throws ObjectNotFoundException;

    Profile findByUid(String uid) throws ObjectNotFoundException;

    Optional<Profile> findByEmail(String email);

    void signUp(Profile profile, boolean uploadProfileAvatar);

    void translitSocialProfile(Profile profile);

    void update(Profile profile);

    void uploadNewAvatar(Profile currentProfile, ImageResource imageResource, AsyncOperation<Profile> asyncOperation);
}
