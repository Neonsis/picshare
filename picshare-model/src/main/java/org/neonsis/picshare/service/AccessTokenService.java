package org.neonsis.picshare.service;

import org.neonsis.picshare.exception.AccessForbiddenException;
import org.neonsis.picshare.exception.InvalidAccessTokenException;
import org.neonsis.picshare.model.domain.AccessToken;
import org.neonsis.picshare.model.domain.Profile;

public interface AccessTokenService {

    AccessToken generateAccessToken(Profile profile);

    Profile findProfile(String token, Long profileId) throws AccessForbiddenException, InvalidAccessTokenException;

    void invalidateAccessToken(String token) throws InvalidAccessTokenException;
}
