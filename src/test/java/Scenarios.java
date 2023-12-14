import com.codeborne.selenide.Configuration;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;
import static org.openqa.selenium.UnexpectedAlertBehaviour.ACCEPT_AND_NOTIFY;
import static org.openqa.selenium.remote.CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR;

public class Scenarios {
    @BeforeEach
    public void configureTest() {
//        System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\chromedriver.exe");
        Configuration.timeout = Long.parseLong("7000");
        Configuration.browserSize = "1900x1200";
         ChromeOptions option = new ChromeOptions();
        option.addArguments("--auto-accept-camera-and-microphone-capture");
        option.addArguments("--disable-input-event-activation-protection");
//        option.addArguments("window-size=1920,1080");
//        option.addArguments("enable-precise-memory-info");
//        option.addArguments("enable-popup-blocking");
//        option.addArguments("disable-default-apps");
//        option.addArguments("test-type=browser");
         option.addArguments("--remote-allow-origins=*");
        Configuration.browserCapabilities = option;

        Configuration.browserCapabilities.setCapability(UNHANDLED_PROMPT_BEHAVIOUR, ACCEPT_AND_NOTIFY);
//        Configuration.proxyEnabled = true;

    }
    private static final String URL = "https://api-clients-test.dev.dion.vc/";
    User user = new User("user534@test.ru", "name", "surname");
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
        Profile.joinMyRoom(1);
    }
    @Test
    public void testProfile() {
        Authorization.execute(user);
        Profile.edit();
    }
    @Test
    public void testChats(){
        Authorization.execute(user);
        Profile.joinChats();
    }

    @Test
    public void rest() {
        open("https://frontend-test.dev.dion.vc/");
        $x("//a[@href='/register']").click();
        $x("//input[@name='name']").val(user.name+" "+user.surname);
        $x("//input[@name='email']").val(user.email);
        $x("//input[@name='password']").val(user.password);
        $x("//input[@name='password2']").val(user.password);
        $x("//input[@name='agreement']").click();
        Wait().withTimeout(Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable($x("//button[@type='submit']")));
        $x("//button[@type='submit']").click();
        List<UserData> users = given().
                when().
                contentType(ContentType.JSON).
                post(URL + "v2/users/register").
                then().log().all().extract().body().jsonPath().getList("",UserData.class);}
}