package gov.iti.rest.exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class APIExceptionMapper implements ExceptionMapper<APIException> {
    @Override
    public Response toResponse(APIException exception) {
        ErrorCode errorCode = new ErrorCode(400 , exception.getMessage());
        return Response.status(400).entity(errorCode).build();
    }
}
