package org.neonsis.picshare.rest.erromapper;

import org.neonsis.picshare.exception.InvalidAccessTokenException;
import org.neonsis.picshare.rest.model.ErrorMessageREST;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
import static org.neonsis.picshare.rest.config.StatusMessages.INVALID_ACCESS_TOKEN;

@Provider
@ApplicationScoped
public class InvalidAccessTokenExceptionMapper implements ExceptionMapper<InvalidAccessTokenException> {

    @Override
    public Response toResponse(InvalidAccessTokenException exception) {
        return Response.
                status(UNAUTHORIZED).
                entity(new ErrorMessageREST(INVALID_ACCESS_TOKEN)).
                type(MediaType.APPLICATION_JSON).
                build();
    }
}
