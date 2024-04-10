package gov.iti.rest.exceptions;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {
    @Override
    public Response toResponse( BadRequestException e ) {
        ErrorCode errorMessage = new ErrorCode(400, e.getMessage());
        return Response.status( 400 ).entity( errorMessage ).build();
    }
}