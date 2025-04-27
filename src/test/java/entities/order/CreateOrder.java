package entities.order;

import entities.Order;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CreateOrder { //создаем заказ без цвета самоката (т.к. это поле не обязательное)

    private static final String ORDER_CREATE_URL = "/api/v1/orders";

    @Step("Create minimal order - without colour")
    public Response createNewOrder(Order order) {
        return given()
                        .header("Content-type", "application/json")
                        .body(order)
                        .when()
                        .post(ORDER_CREATE_URL);
    }
}
