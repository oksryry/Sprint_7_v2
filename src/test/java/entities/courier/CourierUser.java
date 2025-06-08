package entities.courier;

import entities.Courier;
import entities.CourierCreds;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierUser {

    private static final String COURIER_BASE_URL = "/api/v1/courier/";

    private static final String COURIER_AUTHORIZATION_BASE_URL = "/api/v1/courier/login/";

    @Step("Create new courier")
    public Response createCourier(Courier courier) {

        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(COURIER_BASE_URL);
    }

    @Step("Login with created courier")
    public Response courierAuthorization(CourierCreds courierCreds) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courierCreds)
                .when()
                .post(COURIER_AUTHORIZATION_BASE_URL);
    }

    @Step("Delete courier")
    public Response deleteCourier(int id) {
        return given()
                .header("Content-type", "application/json")
                .when()
                .delete(COURIER_BASE_URL + ":" + id);
    }

    @Step("Create Courier without one necessary parameter - login/password")
    public Response createCourierParams(String requestBody) {
        return given()
                .header("Content-type", "application/json")
                .body(requestBody)
                .when()
                .post(COURIER_BASE_URL);
    }

    @Step("Authorize without one necessary parameter - login/password")
    public Response autorizeCourierParams(String requestAuthorizeBody) {
        return given()
                .header("Content-type", "application/json")
                .body(requestAuthorizeBody)
                .when()
                .post(COURIER_AUTHORIZATION_BASE_URL);
    }

}
