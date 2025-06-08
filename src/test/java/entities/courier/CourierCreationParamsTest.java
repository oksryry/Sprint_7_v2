package entities.courier;

import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import utils.Rules;
import utils.UtilsRandomString;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Parameterized.class) //параметризованный тест, проверяет: чтобы создать курьера, нужно передать в ручку все обязательные поля
public class CourierCreationParamsTest {


    private final String requestBodyWithoutOneField;

    private final CourierUser courierUser = new CourierUser();

    public CourierCreationParamsTest(String requestBodyWithoutOneField) {
        this.requestBodyWithoutOneField = requestBodyWithoutOneField;
    }




    @Rule
    public final Rules rule = new Rules();

    @Parameterized.Parameters(name = "{index}: При наборе данных для регистрации курьера {0}")
    public static Collection<Object[]> courierParams() {
        Faker faker = new Faker(Locale.forLanguageTag("ru"));
        return Arrays.asList(new Object[][] {
                {String.format("{ \"password\": \"%s\", \"firstName\": \"%s\" }", faker.bothify("????####"), faker.name().firstName())},
                {String.format("{ \"login\": \"%s\", \"firstName\": \"%s\" }", UtilsRandomString.randomString(), faker.name().firstName())},
        }); //оставила один самописный генератор как памятку для себя, что можно и так, и так
    }

    @Test
    @DisplayName("Check if create courier without necessary parameter returns 400 Not enough data to create courier")
    public void createCourierWithoutOneParameter() {
        courierUser.createCourierParams(requestBodyWithoutOneField)
                .then()
                .assertThat().body("message",equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);

    }
}
