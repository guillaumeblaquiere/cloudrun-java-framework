package fr.gblaquiere.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelloWorld extends HttpServlet {

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws
            IOException {

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("Hello World!");
    }
}