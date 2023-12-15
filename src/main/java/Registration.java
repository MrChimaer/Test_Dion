
import com.codeborne.selenide.Configuration;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.proxy.CaptureType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import static com.codeborne.selenide.Selenide.*;


public class Registration {

    public static void execute(User user) {
        System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\chromedriver.exe");
        Configuration.proxyEnabled = true;
        BrowserMobProxy bmp = new BrowserMobProxyServer();
        bmp.setHarCaptureTypes(CaptureType.getAllContentCaptureTypes());
        bmp.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
        bmp.newHar("https://frontend-test.dev.dion.vc/");

        String query = "https://frontend-test.dev.dion.vc/";
        open(query);
        $x("//a[@href='/register']").click();
        $x("//input[@name='name']").val(user.name + " " + user.surname);
        $x("//input[@name='email']").val(user.email);
        $x("//input[@name='password']").val(user.password);
        $x("//input[@name='password2']").val(user.password);
        $x("//input[@name='agreement']").click();
        Wait().withTimeout(Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable($x("//button[@type='submit']")));
        $x("//button[@type='submit']").click();
        String pin;
        List<HarEntry> requests = bmp.getHar().getLog().getEntries();
        List<String> pins = requests.stream().filter(s -> s.contains(" = "))
                .map(s -> s.split(" = ")[1].trim())
                .collect(Collectors.toList());
        pin = pins.get(0);

        $x("//input[@name='pin']").val(pin);
        $x("//button[@type='submit']").click();
        sleep(1000);
        $x("//h5[contains(text(),'Мои комнаты')]").isDisplayed();
    }

}
