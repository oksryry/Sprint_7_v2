package entities.order;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrdersListTests {

    private static final String ORDERS_URL = "/api/v1/orders";
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test //получаем список вообще всех существующих заказов
    @Step("Get list of all orders")
    public void getOrdersList() {
        given()
                .get(ORDERS_URL)
                .then()
                .assertThat().body("orders", notNullValue());

    }

}
