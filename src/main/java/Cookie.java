import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Driver;
import com.codeborne.selenide.SelenideTargetLocator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static com.codeborne.selenide.Selenide.*;

public class Cookie {
    public static String policy_URL = "https://frontend-test.dev.dion.vc/policy";
    public static String policy_Agreement = "https://dion.vc/agreement";


    public static void check() {
        WebDriver driver = new ChromeDriver();
        SelenideTargetLocator selenideTargetLocator = switchTo();
        open("https://frontend-test.dev.dion.vc/");
        clearBrowserCookies();
        refresh();
        $x("//a[@id='go-to-policy']").is(Condition.interactable);
        $x("//a[@id='go-to-policy']").click();
        if (driver.getCurrentUrl() == policy_URL) {
            $x("//h4[contains(text(),'Политика конфиденциальности сервиса «DION»')]");
        }
        $x("//a[@href='" + policy_Agreement + "']").click();
        $x("//a[@id='back-link']").click();
        selenideTargetLocator.window(0);
        $x("//button[@id='cookie-accept']").click();
        $x("//button[@id='cookie-accept']").is(Condition.disabled);
    }
}