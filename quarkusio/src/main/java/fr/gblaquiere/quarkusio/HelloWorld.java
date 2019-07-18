package fr.gblaquiere.quarkusio;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/api")
public class HelloWorld {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/")
    public String hello() {
        return "Hello World !";
    }
}