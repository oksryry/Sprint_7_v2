package utils;

import com.github.javafaker.Faker;
import entities.Courier;

public class CourierParametersSetting {  //генератор данных курьера
    public Faker faker = new Faker();

    public Courier setParameters() {


        Courier courier = new Courier();
        courier.setFirstName(faker.name().firstName());
        courier.setLogin(UtilsRandomString.randomString());
        courier.setPassword(UtilsRandomString.randomString());
        return courier;
    }
}
