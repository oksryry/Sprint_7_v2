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

@RunWith(Parameterized.class) //параметризованный тест, проверяет: чтобы создать курьера, нужно передать в ручку все обязательные поля
public class CourierCreationParamsTest {

    private static final String COURIER_BASE_URL = "/api/v1/courier/";

    private final String requestBodyWithoutOneField;

    public CourierCreationParamsTest(String requestBodyWithoutOneField) {
        this.requestBodyWithoutOneField = requestBodyWithoutOneField;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Parameterized.Parameters(name = "{index}: При наборе данных для регистрации курьера {0}")
    public static Collection<Object[]> courierParams() {
        return Arrays.asList(new Object[][] {
                {String.format("{ \"password\": \"%s\", \"firstName\": \"%s\" }", UtilsRandomString.randomString(), UtilsRandomString.randomString())},
                {String.format("{ \"login\": \"%s\", \"firstName\": \"%s\" }", UtilsRandomString.randomString(), UtilsRandomString.randomString())},
        });
    }

    @Test
    @Step("Check if create courier without necessary parameter returns 400 Not enough data to create courier")
    public void createCourierWithoutOneParameter() {
        given()
                .header("Content-type", "application/json")
                .body(requestBodyWithoutOneField)
                .when()
                .post(COURIER_BASE_URL)
                .then()
                .assertThat().body("message",equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);

    }
}
