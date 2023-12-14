
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import static com.codeborne.selenide.Selenide.*;

public class Profile {
    public static void createRoom() {
        sleep(2000);
        refresh();
        Wait().withTimeout(Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable($x("//button[contains(text(),'Создать')]")));
        $x("//button[contains(text(),'Создать')]").click();
        $x("//p[contains(text(),'Быстрая ссылка')]").click();
        String linkText = $x("//section//p").getText();
        $x("//section//button[@type='submit']").click();
        $x("//button[@id='connect-to-call']").click();
        $x("//div[@id='call_success']").exists();
        System.out.println("ссылка на конференцию" + linkText);
        sleep(5000);
    }

    public static void joinMyRoom(int roomNumber){
        $x(String.format("//a[@id='my-room-%s']", roomNumber)).click();
        $x("//button[@id='connect-to-call']").click();
        $x("//div[@id='call_success']").exists();
    }

    public static void edit(){
    $x("//button[@title='Меню']").click();
    $x("//p[contains(text(),'Настройки')]").click();
    $x("//h4[contains(text(),'Профиль')]").isDisplayed();
    $x("//input[@id='profile-position']").val("Engineer");
    $x("//button[@id='update-profile']").click();
    }

    public static void joinChats(){
        Wait().withTimeout(Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable($x("//button[contains(text(),'Хорошо')]")));
        $x("//button[contains(text(),'Хорошо')]").click();
        $x("//span[contains(text(),'Чаты')]").click();
        $x("//a[contains(text(),'Чаты')]").isDisplayed();


    }

}
