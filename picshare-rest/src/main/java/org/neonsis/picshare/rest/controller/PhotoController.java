package org.neonsis.picshare.rest.controller;

import org.neonsis.picshare.common.converter.ModelConverter;
import org.neonsis.picshare.service.PhotoService;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/photo")
@Produces({APPLICATION_JSON})
@RequestScoped
public class PhotoController {

    @EJB
    private PhotoService photoService;
    
    @Inject
    private ModelConverter converter;
}
