import static com.codeborne.selenide.Selenide.*;

public class Authorization {


    public static void execute(User user) {
        open("https://frontend-test.dev.dion.vc/");
        $x("//button[contains(text(),'Войти')]").click();
        $x("//input[@name='email']").val(user.email);
        $x("//input[@name='password']").val(user.password);
        $x("//button[@type='submit']").click();
        sleep(30000);
    }
    public static void typeWrongPassword(User user){
        open("https://frontend-test.dev.dion.vc/");
        $x("//button[contains(text(),'Войти')]").click();
        $x("//input[@name='email']").val(user.email);
        $x("//input[@name='password']").val(user.wrong_password);
        $x("//button[@type='submit']").click();
        $x("//div[contains(text(),'Неверный логин или пароль')]").isDisplayed();
    }

    public static void recoverPassword(User user){
        typeWrongPassword(user);
        $x("//button[@id='restore-password-link']").click();
        $x("//input[@id='email']").val(user.email);
        $x("//button[@id='reset-password-button']").click();
        //получаем пин из ответа
        String pin = "";
        $x("//input[@id='pin']").val(pin);
        $x("//input[@id='password']").val(user.password);
        $x("//input[@id='password_confirm']").val(user.password);
        $x("//button[@type='submit']").click();
    }
}