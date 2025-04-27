package entities.order;

import entities.Order;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import utils.OrderObligatoryFieldsSetting;
import java.util.Arrays;
import java.util.Collection;
import static org.apache.hc.core5.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreationTest {

    // создаём объект генератора заказа с обязательными полями
    private OrderObligatoryFieldsSetting orderObligatoryFieldsSetting = new OrderObligatoryFieldsSetting();

    //создаем сущность (объект) заказа с обязательными полями
    private Order orderWithMinFields = orderObligatoryFieldsSetting.setObligatoryFields();

    CreateOrder createOrder = new CreateOrder();

    private String[] colours; //поле, куда будут подставляться разные параметры

    //конструктор, принимающий параметры
    public OrderCreationTest(String[] colours) {
        this.colours = colours;
    }



    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Parameterized.Parameters(name = "{index}: При наборе параметров цвета {0}")
    public static Collection<Object[]> coloursParameters() {
        return Arrays.asList(new Object[][] {
                { new String[]{"BLACK"} },       // 1 вариант: BLACK
                { new String[]{"GREY"} },        // 2 вариант: GREY
                { new String[]{"BLACK", "GREY"} }, // 3 вариант: оба цвета
                { null }
        });
    }

    @Test //поверяем, можно указать один из цветов — BLACK или GREY;
    //можно указать оба цвета;
    //можно совсем не указывать цвет;
    //тело ответа содержит track.
    @Step("Check if it is possible to place an order with different values or null value in colour")
    public void orderWithDifferentColours() {
        Order fullOrder = orderWithMinFields.setColor(colours);
        Response response = createOrder.createNewOrder(fullOrder);
        response.then().statusCode(SC_CREATED).and().body("track", notNullValue());
    }
}
