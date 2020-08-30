package org.neonsis.picshare.rest.erromapper;

import org.neonsis.picshare.exception.ValidationException;
import org.neonsis.picshare.rest.model.ErrorMessageREST;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

@Provider
@ApplicationScoped
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

    @Override
    public Response toResponse(ValidationException exception) {
        return Response.
                status(BAD_REQUEST).
                entity(new ErrorMessageREST(exception.getMessage(), true)).
                type(MediaType.APPLICATION_JSON).
                build();
    }
}
