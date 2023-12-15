import com.codeborne.selenide.Configuration;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.proxy.CaptureType;
import java.util.List;
import java.util.stream.Collectors;
import static com.codeborne.selenide.Selenide.*;

public class Authorization {


    public static void execute(User user) {
        open("https://frontend-test.dev.dion.vc/");
        $x("//button[@id='go-to-signin-link']").click();
        $x("//input[@name='email']").val(user.email);
        $x("//input[@name='password']").val(user.password);
        $x("//button[@type='submit']").click();
        sleep(3000);
    }

    public static void typeWrongPassword(User user) {

        open("https://frontend-test.dev.dion.vc/");
        sleep(2000);
        $x("//button[@id='go-to-signin-link']").click();
        $x("//input[@name='email']").val(user.email);
        $x("//input[@name='password']").val(user.wrong_password);
        $x("//button[@type='submit']").click();
        $x("//div[contains(text(),'Неверный логин или пароль')]").isDisplayed();
    }

    public static void recoverPassword(User user) {
        System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\chromedriver.exe");
        Configuration.proxyEnabled = true;
        BrowserMobProxy bmp = new BrowserMobProxyServer();
        bmp.setHarCaptureTypes(CaptureType.getAllContentCaptureTypes());
        bmp.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
        bmp.newHar("https://frontend-test.dev.dion.vc/");

        String query = "https://frontend-test.dev.dion.vc/";
        open(query);
        $x("//button[@id='restore-password-link']").click();
        $x("//input[@id='email']").val(user.email);
        $x("//button[@id='reset-password-button']").click();
        String pin;
        List<HarEntry> requests = bmp.getHar().getLog().getEntries();
        List<String> pins = requests.stream().filter(s -> s.contains(" = "))
                .map(s -> s.split(" = ")[1].trim())
                .collect(Collectors.toList());
        pin = pins.get(0);

        $x("//input[@id='pin']").val(pin);
        $x("//input[@id='password']").val(user.password);
        $x("//input[@id='password_confirm']").val(user.password);
        $x("//button[@type='submit']").click();
    }
}