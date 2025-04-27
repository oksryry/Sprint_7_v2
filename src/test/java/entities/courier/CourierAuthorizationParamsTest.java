package entities.courier;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import utils.UtilsRandomString;

import java.util.Arrays;
import java.util.Collection;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Parameterized.class)
public class CourierAuthorizationParamsTest {

    private static final String COURIER_BASE_URL = "/api/v1/courier/login";


    private final String requestLoginBodyWithoutOneField;

    public CourierAuthorizationParamsTest(String requestLoginBodyWithoutOneField) {
        this.requestLoginBodyWithoutOneField = requestLoginBodyWithoutOneField;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Parameterized.Parameters (name = "{index}: При наборе данных для авторизации {0}")
    public static Collection<Object[]> courierLoginParams() {
        return Arrays.asList(new Object[][] {
                {String.format("{ \"password\": \"%s\"}", UtilsRandomString.randomString())},
                {String.format("{ \"login\": \"%s\"}", UtilsRandomString.randomString())},
        });
    }

    @Test //для авторизации нужно передать все обязательные поля; если какого-то поля нет, запрос возвращает ошибку
    @Step("Check courier login without login OR password - must be 400 Not enough data to log in")
    public void сourierLoginWithoutOneParameter() {
        given()
                .header("Content-type", "application/json")
                .body(requestLoginBodyWithoutOneField)
                .when()
                .post(COURIER_BASE_URL)
                .then()
                .assertThat().body("message",equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);

    }

}
