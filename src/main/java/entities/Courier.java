package entities;

import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;

import static io.restassured.RestAssured.given;

@Getter @Setter
public class Courier {



    private String login;
    private String password;
    private String firstName;

    private static final String COURIER_BASE_URL = "/api/v1/courier/";

    public Courier() {};

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }




    //сделаем метод с созданием курьера здесь, чтобы потом использовать этот метод в тестовом классе
//    public Response createCourier(String login, String password, String firstName) {
//    return given()
//            .header("Content-type", "application/json")
//            .body(String login, String password, String firstName)
//            .when()
//            .post(COURIER_BASE_URL);
//    }


}
