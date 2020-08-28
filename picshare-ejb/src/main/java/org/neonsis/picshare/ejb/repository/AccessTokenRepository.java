package org.neonsis.picshare.ejb.repository;

import org.neonsis.picshare.model.domain.AccessToken;

import java.util.Optional;

public interface AccessTokenRepository extends EntityRepository<AccessToken, String> {

    Optional<AccessToken> findByToken(String token);

    boolean removeAccessToken(String token);
}
