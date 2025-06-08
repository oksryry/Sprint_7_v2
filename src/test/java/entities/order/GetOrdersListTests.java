package entities.order;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Rule;
import org.junit.Test;
import utils.Rules;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.CoreMatchers.not;


public class GetOrdersListTests {

    private CreateOrder getOrders = new CreateOrder();

    @Rule
    public final Rules rule = new Rules();

    @Test //получаем список вообще всех существующих заказов
    @DisplayName("Get list of all orders")
    public void getOrdersList() {
        getOrders.getAllOrders()
                .then()
                .assertThat().body("orders", not(empty()));

    }

}
