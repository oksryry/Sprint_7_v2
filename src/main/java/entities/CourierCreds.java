package entities;

public class CourierCreds {

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String login;
    private String password;
    private CourierCreds(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static CourierCreds getCourierCreds(Courier courier) {
        return new CourierCreds(courier.getLogin(), courier.getPassword());
    }
}
