package service;

import net.engineeringdigest.journalApp.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {
    @Autowired
    private EmailService emailService;

    @Test
    void  testSendMail(){
        emailService.sendEmail("mymail@gmail.com","Testing java mail sender.","Hola! How are you?");
    }
}
