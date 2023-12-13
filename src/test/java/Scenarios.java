import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.codeborne.selenide.Selenide.$x;

public class Scenarios {
    @BeforeEach
    public void configureTest() {
        Configuration.timeout = Long.parseLong("20000");
        Configuration.browserSize = "1900x1200";
        Configuration.browserCapabilities = new ChromeOptions().addArguments("--remote-allow-origins=*");
    }

    User user = new User("user345@test.ru", "name", "surname");
    User existingUser = new User ("user1@test.ru", "name", "surname");
    User wrongUser = new User ("user696@test.ru", "wrong", "wrong");

    @Test
    public void testRegistration(){
        Registration.execute(user);
    }

    @Test
    public void testRegistrationExistingUser(){
        Registration.execute(existingUser);
        $x("//div[@role='alert']//div[contains(text(),'Пользователь с такой почтой уже существует']").isDisplayed();
    }

    @Test
    public void testAuth(){
        Authorization.execute(user);
    }

    @Test
    public void testWrongPassword(){
        Authorization.typeWrongPassword(user);
    }

    @Test
    public void recoverPassword(){
        Authorization.typeWrongPassword(user);
    }

    @Test
    public void testCookie(){
        Cookie.check();
    }
}