package org.neonsis.picshare.rest.controller;

import org.neonsis.picshare.common.converter.ModelConverter;
import org.neonsis.picshare.model.Pageable;
import org.neonsis.picshare.model.domain.Photo;
import org.neonsis.picshare.model.domain.Profile;
import org.neonsis.picshare.rest.model.PhotosREST;
import org.neonsis.picshare.rest.model.ProfilePhotoREST;
import org.neonsis.picshare.rest.model.ProfileWithPhotosREST;
import org.neonsis.picshare.service.PhotoService;
import org.neonsis.picshare.service.ProfileService;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.neonsis.picshare.rest.config.Constants.PHOTO_LIMIT;

@Path("/profile")
@Produces({APPLICATION_JSON})
@RequestScoped
public class PublicProfileController {

    @EJB
    private ProfileService profileService;

    @EJB
    private PhotoService photoService;

    @Inject
    private ModelConverter converter;

    @GET
    @Path("/{id}")
    public ProfileWithPhotosREST findProfile(
            @PathParam("id") Long id,
            @DefaultValue("false") @QueryParam("withPhotos") boolean withPhotos,
            @DefaultValue(PHOTO_LIMIT) @QueryParam("limit") int limit) {
        Profile p = profileService.findById(id);
        ProfileWithPhotosREST profile = converter.convert(p, ProfileWithPhotosREST.class);
        if (withPhotos) {
            List<Photo> photos = photoService.findProfilePhotos(profile.getId(), new Pageable(1, limit));
            profile.setPhotos(converter.convertList(photos, ProfilePhotoREST.class));
        }
        return profile;
    }

    @GET
    @Path("/{profileId}/photos")
    public PhotosREST findProfilePhotos(
            @PathParam("profileId") Long profileId,
            @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue(PHOTO_LIMIT) @QueryParam("limit") int limit) {
        List<Photo> photos = photoService.findProfilePhotos(profileId, new Pageable(page, limit));
        return new PhotosREST(converter.convertList(photos, ProfilePhotoREST.class));
    }
}
