public class User {
    public String email;
    public String name;
    public String surname;
    public String password;
    public String wrong_password;
    public User(String email, String name, String surname){
        this.email=email;
        this.password  = email + "Tt1?";
        this.name= name;
        this.surname=surname;
        this.wrong_password="incorrect_password";
    }
}