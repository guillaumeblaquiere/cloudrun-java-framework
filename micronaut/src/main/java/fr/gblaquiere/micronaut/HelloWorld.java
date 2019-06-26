package fr.gblaquiere.micronaut;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/api")
public class HelloWorld {

    @Get("/")
    public String hello() {
        return "Hello World!";
    }
}