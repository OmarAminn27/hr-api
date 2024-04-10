package gov.iti.rest.exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ThrowableMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable throwable) {
        ErrorCode errorMessage = new ErrorCode(400, throwable.getMessage());
        return Response.status( 400 ).entity( errorMessage ).build();
    }
}