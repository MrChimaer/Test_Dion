import com.codeborne.selenide.WebDriverRunner;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.CaptureType;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

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
    public static void typeWrongPassword(User user){
        open("https://frontend-test.dev.dion.vc/");
        $x("//button[contains(text(),'Войти')]").click();
        $x("//input[@name='email']").val(user.email);
        $x("//input[@name='password']").val(user.wrong_password);
        $x("//button[@type='submit']").click();
        $x("//div[contains(text(),'Неверный логин или пароль')]").isDisplayed();
        sleep(3000);
    }

    public static void recoverPassword(User user){
        typeWrongPassword(user);
        $x("//button[@id='restore-password-link']").click();
        $x("//input[@id='email']").val(user.email);
        $x("//button[@id='reset-password-button']").click();
        //получаем пин из ответа

        // start the proxy
        BrowserMobProxy proxy = new BrowserMobProxyServer();
        proxy.start(0);

        // get the Selenium proxy object
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);

        // configure it as a desired capability
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);

        // start the browser up
        WebDriver driver = new ChromeDriver();

        // enable more detailed HAR capture, if desired (see CaptureType for the complete list)
        proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);

        // create a new HAR with the label "yahoo.com"
        proxy.newHar("yahoo.com");

        // open yahoo.com
        driver.get("http://yahoo.com");

        // get the HAR data
        Har har = proxy.getHar();




//        BrowserMobProxyServer bmp = WebDriverRunner.getSelenideProxy().getProxy();
//
//        // запоминать тело запросов (по умолчанию тело не запоминается, ибо может быть большим)
//        bmp.setHarCaptureTypes(CaptureType.getAllContentCaptureTypes());
//
//        // запоминать как запросы, так и ответы
//        bmp.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
//
//        // начинай запись!
//        bmp.newHar("pofig");

        String pin = har.toString();
        $x("//input[@id='pin']").val(pin);
        $x("//input[@id='password']").val(user.password);
        $x("//input[@id='password_confirm']").val(user.password);
        $x("//button[@type='submit']").click();
    }
}