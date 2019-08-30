package fr.gblaquiere.kotlin.function;

import javax.servlet.http.*;

class HelloWorld{
    fun helloWorld(req: HttpServletRequest, resp: HttpServletResponse) {
        with(resp.writer) {
            println("Hello World!")
        }
    }
}