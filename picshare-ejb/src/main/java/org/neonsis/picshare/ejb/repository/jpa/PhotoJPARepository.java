package org.neonsis.picshare.ejb.repository.jpa;

import org.neonsis.picshare.ejb.repository.PhotoRepository;
import org.neonsis.picshare.model.domain.Photo;

import javax.enterprise.context.Dependent;
import java.util.List;

import static org.neonsis.picshare.ejb.repository.jpa.StaticJPAQueryInitializer.JPAQuery;

@Dependent
public class PhotoJPARepository extends AbstractJPARepository<Photo, Long> implements PhotoRepository {

    @Override
    @JPAQuery("SELECT ph FROM Photo ph WHERE ph.profile.id=:profileId ORDER BY ph.id DESC")
    public List<Photo> findProfilePhotosLatestFirst(Long profileId, int offset, int limit) {
        return em.createNamedQuery("Photo.findProfilePhotosLatestFirst")
                .setParameter("profileId", profileId)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    @JPAQuery("SELECT COUNT(ph) FROM Photo ph WHERE ph.profile.id=:profileId")
    public int countProfilePhotos(Long profileId) {
        Object count = em.createNamedQuery("Photo.countProfilePhotos")
                .setParameter("profileId", profileId)
                .getSingleResult();
        return ((Number) count).intValue();
    }

    @Override
    @JPAQuery("SELECT ph FROM Photo ph JOIN FETCH ph.profile ORDER BY ph.views DESC")
    public List<Photo> findAllOrderByViewsDesc(int offset, int limit) {
        return em.createNamedQuery("Photo.findAllOrderByViewsDesc")
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    @JPAQuery("SELECT ph FROM Photo ph JOIN FETCH ph.profile p ORDER BY p.rating DESC, ph.views DESC")
    public List<Photo> findAllOrderByAuthorRatingDesc(int offset, int limit) {
        return em.createNamedQuery("Photo.findAllOrderByAuthorRatingDesc")
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    @JPAQuery("SELECT COUNT(ph) FROM Photo ph")
    public long countAll() {
        Object count = em
                .createNamedQuery("Photo.countAll")
                .getSingleResult();
        return ((Number)count).intValue();
    }

    @Override
    protected Class<Photo> getEntityClass() {
        return Photo.class;
    }
}
