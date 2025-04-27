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

import static org.apache.hc.core5.http.HttpStatus.SC_CONFLICT;
import static org.apache.hc.core5.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.equalTo;


public class CourierCreationTests {

    private int id;

    private CourierParametersSetting courierParametersSetting = new CourierParametersSetting(); //создаём объект генератора
    private Courier courier = courierParametersSetting.setParameters(); //создаем объект курьера (генериуем его генератором)
    private CourierUser courierUser = new CourierUser(); //создаем объект класса, где есть действия с курьерами



    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }



    @Test //курьера можно создать; запрос вернул правильный код ответа
    @Step("Check creation of Courier method - status Code 201 must be returned")
    public void createCourier() {
        Response response = courierUser.createCourier(courier);
        Assert.assertEquals("Курьер успешно создан", SC_CREATED, response.statusCode());
    }

    @Test //поверяем, что нельзя создать двух одинаковых курьеров
    @Step("Check creation of the same Couriers - must be no opportunity to do this")
    public void createTheSameCourier() {
        courierUser.createCourier(courier);
        Response response = courierUser.createCourier(courier);
        Assert.assertEquals("Курьер с такими данными создан с системе ранее", SC_CONFLICT, response.statusCode());


    }

    @Test //успешный запрос возвращает ok: true;
    @Step("Check creation of Courier method - response must be TRUE")
    public void checkCourierCreationResponse() {
        Response response = courierUser.createCourier(courier);
        response.then()
                .body("ok", equalTo(true));
    }

    @After
    public void tearDown() {
        id = courierUser.courierAuthorization(CourierCreds.getCourierCreds(courier)).as(courierIdInLoginResponse.class).getId();
        courierUser.deleteCourier(id);
    }

}
