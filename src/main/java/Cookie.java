import com.codeborne.selenide.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static com.codeborne.selenide.Selenide.*;

public class Cookie {
    public static String policy_URL = "https://frontend-test.dev.dion.vc/policy";
    public static String policy_Agreement = "https://dion.vc/agreement";


    public static void check() {
        SelenideDriver driver = new SelenideDriver(new SelenideConfig());

        open("https://frontend-test.dev.dion.vc/");
        clearBrowserCookies();
        refresh();
        $x("//a[@id='go-to-policy']").is(Condition.interactable);
        $x("//a[@id='go-to-policy']").click();

        $x("//h4[contains(text(),'Политика конфиденциальности сервиса «DION»')]");
        Selenide.switchTo().window(1);

//        for (String tab: driver.getWindowHandles()){driver.switchTo().window(tab);}
        $x("//a[@href='" + policy_Agreement + "']").click();
        Selenide.switchTo().window(2);
        $x("//a[@id='back-link']").click();

        Selenide.switchTo().window(0);
        $x("//button[@id='cookie-accept']").click();
        $x("//button[@id='cookie-accept']").is(Condition.disabled);
    }
}