package entities.courier;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import utils.Rules;

import java.util.Arrays;
import java.util.Collection;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Parameterized.class)
public class CourierAuthorizationParamsTest {


    private final String requestLoginBodyWithoutOneField;

    private final CourierUser courierUser = new CourierUser();

    public CourierAuthorizationParamsTest(String requestLoginBodyWithoutOneField) {
        this.requestLoginBodyWithoutOneField = requestLoginBodyWithoutOneField;
    }


    @Rule
    public final Rules rule = new Rules();

    @Parameterized.Parameters (name = "{index}: При наборе данных для авторизации {0}")
    public static Collection<Object[]> courierLoginParams() {
        Faker faker = new Faker();
        return Arrays.asList(new Object[][] {
                {String.format("{ \"password\": \"%s\"}", faker.bothify("????####"))},
                {String.format("{ \"login\": \"%s\"}", faker.bothify("????####"))},
        });
    }

    @Test //для авторизации нужно передать все обязательные поля; если какого-то поля нет, запрос возвращает ошибку
    @DisplayName("Check courier login without login OR password - must be 400 Not enough data to log in")
    public void сourierLoginWithoutOneParameter() {
      courierUser.autorizeCourierParams(requestLoginBodyWithoutOneField)
                .then()
                .assertThat().body("message",equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);

    }

}
