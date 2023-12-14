public class UserData {

    private Integer attempts_left;
    private String session_key;
    private String pin;
    public UserData(String pin,String session_key, Integer attempts_left){
        this.pin=pin;
        this.attempts_left=attempts_left;
        this.session_key=session_key;
    }

    public Integer getAttempts_left() {
        return attempts_left;
    }

    public String getSession_key() {
        return session_key;
    }
    public String getPin() {
        return pin;
    }
}
