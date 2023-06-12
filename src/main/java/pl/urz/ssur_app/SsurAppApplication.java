package pl.urz.ssur_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import pl.urz.ssur_app.service.EmailSenderService;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class SsurAppApplication {
    @Autowired
    private EmailSenderService emailSenderService;

    public static void main(String[] args) {
        SpringApplication.run(SsurAppApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void sendMail(){
        emailSenderService.sendMail("jmazurek.kontakt@gmail.com", "Temat", "Dzięki działa");
    }


}
