import static com.codeborne.selenide.Selenide.*;

public class Authorization {


    public static void execute(User user) {
        open("https://frontend-test.dev.dion.vc/");
        $x("//button[contains(text(),'Войти')]").click();
        $x("//input[@name='email']").val(user.email);
        $x("//input[@name='password']").val(user.password);
        $x("//button[@type='submit']").click();
        sleep(10000);
    }
}