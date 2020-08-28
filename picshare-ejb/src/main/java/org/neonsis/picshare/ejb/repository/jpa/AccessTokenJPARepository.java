package org.neonsis.picshare.ejb.repository.jpa;

import org.neonsis.picshare.ejb.repository.AccessTokenRepository;
import org.neonsis.picshare.model.domain.AccessToken;

import javax.enterprise.context.Dependent;
import java.util.Optional;

import static org.neonsis.picshare.ejb.repository.jpa.StaticJPAQueryInitializer.JPAQuery;

@Dependent
public class AccessTokenJPARepository extends AbstractJPARepository<AccessToken, String> implements AccessTokenRepository {

    @Override
    @JPAQuery("SELECT at FROM AccessToken at JOIN FETCH at.profile WHERE at.token=:token")
    public Optional<AccessToken> findByToken(String token) {
        AccessToken accessToken = (AccessToken) em.createNamedQuery("AccessToken.findByToken")
                .setParameter("token", token)
                .getSingleResult();
        return Optional.of(accessToken);
    }

    @Override
    @JPAQuery("DELETE FROM AccessToken at WHERE at.token=:token")
    public boolean removeAccessToken(String token) {
        int count = em.createNamedQuery("AccessToken.removeAccessToken")
                .setParameter("token", token)
                .executeUpdate();
        return count == 1;
    }

    @Override
    protected Class<AccessToken> getEntityClass() {
        return AccessToken.class;
    }
}
