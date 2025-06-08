package entities.courier;

import com.github.javafaker.Faker;
import entities.Courier;
import entities.CourierCreds;
import entities.CourierIdInLoginResponse;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.*;
import utils.CourierParametersSetting;
import utils.Rules;

import java.util.Locale;

import static entities.CourierCreds.getCourierCreds;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierAuthorizationTests {

    private CourierUser courierUser = new CourierUser();
    private CourierParametersSetting courierParametersSetting = new CourierParametersSetting();
    private Courier courier = courierParametersSetting.setParameters();
    private int id;


    @Rule
    public final Rules rule = new Rules();

    @Before
    public void setUp() {
        courier = courierParametersSetting.setParameters();
    }

    @Step("Create courier for authorization test") // Создаем курьера в начале теста
    public void createCourierForAuthorizationTest() {
        courierUser.createCourier(courier);
    }

    @Step("Courier authorize in the system and get response")
    public Response courierAuthorization() {
        Response response = courierUser.courierAuthorization(getCourierCreds(courier));
        return response;
    }

    @Test //проверка, что курьер может авторизоваться
    @DisplayName("Successful authorization returns 200 status code")
    public void courierSuccessfulAuthorization() {
        createCourierForAuthorizationTest();
        courierAuthorization();
        Assert.assertEquals(200, courierAuthorization().statusCode());
    }



    @Test //проверка, что успешный запрос возвращает id
    @DisplayName("Check if successful authorization returns courier's id")
    public void courierLoginIdResponse() {
        createCourierForAuthorizationTest();
        id = courierUser.courierAuthorization(getCourierCreds(courier))
                .as(CourierIdInLoginResponse.class).getId();
        courierAuthorization()
                .then()
                    .body("id", notNullValue());
    }


    @Test //если авторизоваться под несуществующим пользователем, запрос возвращает ошибку
    @DisplayName("Authorization with invalid creds returns 404")
    public void courierIncorrectLoginResponse() {
        generateFakeCourier();
        authorizeWithFakeCredentials()
                .then()
                    .assertThat()
                    .statusCode(404)
                    .body("message", equalTo("Учетная запись не найдена"));
    }

    @Step("Generate fake courier - with fake credentials to log in")
    private Courier generateFakeCourier() {
        Faker faker = new Faker();
        Faker fakerRU = new Faker(Locale.forLanguageTag("ru"));
        String invLogin = faker.bothify("????####");
        String invPassword = faker.bothify("????####");
        String invFirstName = fakerRU.name().firstName();
        Courier invalidCourier = new Courier(invLogin, invPassword, invFirstName);
        return invalidCourier;
    }

    @Step("Log in with fake courier/fake credentials")
    private Response authorizeWithFakeCredentials()
    {
        Response response = courierUser.courierAuthorization(
                new CourierCreds(generateFakeCourier().getLogin(), generateFakeCourier().getPassword()));
        return response;
    }

    @Step("Delete created courier after test")
    private void deleteCourier() {
        id = courierUser.courierAuthorization(CourierCreds.getCourierCreds(courier)).as(CourierIdInLoginResponse.class).getId();
        courierUser.deleteCourier(id);
    }


}
