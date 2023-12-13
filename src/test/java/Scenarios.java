import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.codeborne.selenide.Selenide.$x;
import static org.openqa.selenium.UnexpectedAlertBehaviour.ACCEPT_AND_NOTIFY;
import static org.openqa.selenium.remote.CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR;

public class Scenarios {
    @BeforeEach
    public void configureTest() {
//        System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\chromedriver.exe");
        Configuration.timeout = Long.parseLong("7000");
        Configuration.browserSize = "1900x1200";
        Configuration.browserCapabilities = new ChromeOptions().addArguments("--remote-allow-origins=*");
        Configuration.browserCapabilities.setCapability(UNHANDLED_PROMPT_BEHAVIOUR, ACCEPT_AND_NOTIFY);
//        Configuration.proxyEnabled = true;
    }

    User user = new User("user1@test.ru", "name", "surname");
    User existingUser = new User ("user1@test.ru", "name", "surname");
    User wrongUser = new User ("user696@test.ru", "wrong", "wrong");

    @Test
    public void testRegistration(){
        Registration.execute(user);
    }

    @Test
    public void testRegistrationExistingUser(){
        Registration.execute(existingUser);
        $x("//div[contains(text(),'Пользователь с такой почтой уже существует')]").isDisplayed();
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
        Authorization.recoverPassword(user);
    }

    @Test
    public void testCookie(){
        Cookie.check();
    }


    @Test
    public void testRoomCreation(){
    Authorization.execute(user);
    Profile.createRoom();
    }

    @Test
    public void testRoomJoin(){
        Authorization.execute(user);
        Profile.joinRoom(1);
    }
}