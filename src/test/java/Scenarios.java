import org.junit.jupiter.api.Test;

public class Scenarios {
    @Test
    public void testAuth(){
        User user = new User("user1@test.ru");
        Authorization.loginWith(user);

    }
}
