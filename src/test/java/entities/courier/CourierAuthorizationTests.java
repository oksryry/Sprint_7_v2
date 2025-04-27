package entities.courier;

import entities.Courier;
import entities.CourierCreds;
import entities.courierIdInLoginResponse;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.CourierParametersSetting;

import static entities.CourierCreds.getCourierCreds;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierAuthorizationTests {

    private CourierUser courierUser = new CourierUser();
    private CourierParametersSetting courierParametersSetting = new CourierParametersSetting();
    private Courier courier = courierParametersSetting.setParameters();
    private int id;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Step("Create courier for authorization test")
    public void createCourierForAuthorizationTest() {
        courierUser.createCourier(courier);
    }

    @Step("Courier authorize in the system and get response")
    public Response courierAuthorization() {
        Response response = courierUser.courierAuthorization(getCourierCreds(courier));
        return response;
    }

    @Step("Return authorization status Code")
    public int courierAuthorizationStatusCode() {
        int code = courierAuthorization().statusCode();
        return code;
    }

    @Step("Compare authorization response to expected response")
    public void checkResponse(int status, int code) {
        Assert.assertEquals( 200, courierAuthorizationStatusCode());
    }


    @Test //проверка, что курьер может авторизоваться
    @Step("Check if courier with valid creds can be logged in - must be status code 200")
    public void courierSuccessfulAuthorization() {
        createCourierForAuthorizationTest();
        courierAuthorization();
        checkResponse(200, courierAuthorization().statusCode());





    }

    @Test //проверка, что успешный запрос возвращает id
    @Step("Check if successful log in returns courier's id")
    public void courierLoginIdResponse() {
        courierUser.createCourier(courier);
        Response courierAuthorizationResponse = courierUser.courierAuthorization(getCourierCreds(courier));
        courierAuthorizationResponse.then().body("id", notNullValue());

    }

    @Test //если авторизоваться под несуществующим пользователем, запрос возвращает ошибку
    @Step("Check if log in with invalid creds returns 404 No such user found")
    public void courierIncorrectLoginResponse() {
        Response courierIncorrectLoginResponse = courierUser.courierAuthorization(getCourierCreds(courier));
        courierIncorrectLoginResponse.then().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);

    }

    @After
    public void tearDown() {
        id = courierUser.courierAuthorization(CourierCreds.getCourierCreds(courier)).as(courierIdInLoginResponse.class).getId();
        courierUser.deleteCourier(id);
    }
}
