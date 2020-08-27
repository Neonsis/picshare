package org.neonsis.picshare.service;

import org.neonsis.picshare.exception.RetrieveSocialDataFailedException;
import org.neonsis.picshare.model.domain.Profile;

public interface SocialService {

    Profile fetchProfile(String code) throws RetrieveSocialDataFailedException;

    String getClientId();

    default String getAuthorizeUrl() {
        throw new UnsupportedOperationException();
    }
}
