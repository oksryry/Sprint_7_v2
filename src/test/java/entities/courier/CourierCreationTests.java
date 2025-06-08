package entities.courier;

import entities.Courier;
import entities.CourierCreds;
import entities.CourierIdInLoginResponse;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.*;
import utils.CourierParametersSetting;
import utils.Rules;

import static org.apache.hc.core5.http.HttpStatus.SC_CONFLICT;
import static org.apache.hc.core5.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.equalTo;


public class CourierCreationTests {

    private int id;

    private CourierParametersSetting courierParametersSetting = new CourierParametersSetting(); //создаём объект генератора
    private Courier courier = courierParametersSetting.setParameters(); //создаем объект курьера (генериуем его генератором)
    private CourierUser courierUser = new CourierUser(); //создаем объект класса, где есть действия с курьерами



    @Rule
    public final Rules rule = new Rules();

    @Step("Create courier")
    private Response createCourierForCreationTests() {
        Response response = courierUser.createCourier(courier);
        return response;
    }




    @Test //курьера можно создать; запрос вернул правильный код ответа
    @DisplayName("Check creation of Courier method - status Code 201 must be returned")
    public void createCourier() {
        Assert.assertEquals("Курьер успешно создан", SC_CREATED, createCourierForCreationTests().statusCode());
    }

    @Test //поверяем, что нельзя создать двух одинаковых курьеров
    @DisplayName("Check creation of the same Couriers - must be no opportunity to do this")
    public void createTheSameCourier() {
        createCourierForCreationTests();
        createCourierForCreationTests();
        Assert.assertEquals("Курьер с такими данными создан с системе ранее", SC_CONFLICT, createCourierForCreationTests().statusCode());


    }

    @Test //успешный запрос возвращает ok: true;
    @DisplayName("Check creation of Courier method - response must be TRUE")
    public void checkCourierCreationResponse() {
        createCourierForCreationTests()
                .then()
                    .body("ok", equalTo(true));
    }

    @After
    public void tearDown() {
        id = courierUser.courierAuthorization(CourierCreds.getCourierCreds(courier)).as(CourierIdInLoginResponse.class).getId();
        courierUser.deleteCourier(id);
    }

}
