package gov.iti;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/messages")
public class HelloWorld {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String helloRest(){
        return "Hello REST!";
    }
}
