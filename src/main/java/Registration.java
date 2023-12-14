import com.codeborne.selenide.Selenide;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.core.har.HarLog;
import net.lightbody.bmp.proxy.CaptureType;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.*;

public class Registration {
    public static String pin;

    public static void execute(User user) {
        System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\chromedriver.exe");
        BrowserMobProxy proxy = new BrowserMobProxyServer();
        proxy.start();
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
        proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
        proxy.newHar("recover password");

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setCapability(CapabilityType.PROXY, seleniumProxy);
        chromeOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        WebDriver driver = new ChromeDriver(chromeOptions);
        Selenide.using(driver, () -> {
            open("https://frontend-test.dev.dion.vc/");
            $x("//a[@href='/register']").click();
            $x("//input[@name='name']").val(user.name + " " + user.surname);
            $x("//input[@name='email']").val(user.email);
            $x("//input[@name='password']").val(user.password);
            $x("//input[@name='password2']").val(user.password);
            $x("//input[@name='agreement']").click();
            Wait().withTimeout(Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable($x("//button[@type='submit']")));
            $x("//button[@type='submit']").click();

            HarLog log = proxy.getHar().getLog();

            List<HarEntry> post = log.getEntries()
                    .stream()
                    .filter(entry -> entry.getRequest().getMethod().equalsIgnoreCase("post"))
                    .collect(Collectors.toList());

            System.out.println(log);

        });
//        $x("//input[@name='pin']").val(pin);

//        sleep(5000);


    }
}
