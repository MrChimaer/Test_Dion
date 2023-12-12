import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class Registration {
    public static String pin;
    public static void execute(User user) {
        open("https://frontend-test.dev.dion.vc/");
        $x("//a[@href='/register']").click();
        $x("//input[@name='name']").val(user.name+" "+user.surname);
        $x("//input[@name='email']").val(user.email);
        $x("//input[@name='password']").val(user.password);
        $x("//input[@name='password2']").val(user.password);
        $x("//input[@name='agreement']").click();
        Wait().withTimeout(Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable($x("//button[@type='submit']")));
        $x("//button[@type='submit']").click();

//        $x("//input[@name='pin']").val(pin);

//        sleep(5000);


    }
}
