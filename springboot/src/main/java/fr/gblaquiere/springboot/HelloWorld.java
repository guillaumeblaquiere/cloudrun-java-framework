package fr.gblaquiere.springboot;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/")
class HelloWorld{

    @GetMapping("/")
    public ResponseEntity<?> hello(){
        return new ResponseEntity<>("Hello World!", HttpStatus.OK);
    }
}