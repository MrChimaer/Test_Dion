import com.codeborne.selenide.Selenide;

import static com.codeborne.selenide.Selenide.*;

public class Profile {
    public static void createRoom() {
        sleep(500);
        refresh();
        $x("//button[contains(text(),'Создать')]").click();
        $x("//p[contains(text(),'Быстрая ссылка')]").click();

    }
    public static void joinRoom(int roomNumber){
        $x(String.format("//a[@id='my-room-%s']", roomNumber)).click();

        Selenide.switchTo().alert().accept();

        $x("//button[@id='connect-to-call']").click();
    }
}
