package stas.paliutin.jm_311;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class Jm311Application {

    public static void main(String[] args) {
        SpringApplication.run(Jm311Application.class, args);
        System.out.println(" +++++++ ++++++ ++++++" + LocalDateTime.now() );
    }

}
