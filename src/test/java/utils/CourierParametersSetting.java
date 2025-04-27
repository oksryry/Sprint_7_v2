package utils;

import entities.Courier;

public class CourierParametersSetting {  //генератор данных курьера

    public Courier setParameters() {
        Courier courier = new Courier();
        courier.setFirstName(UtilsRandomString.randomString());
        courier.setLogin(UtilsRandomString.randomString());
        courier.setPassword(UtilsRandomString.randomString());
        return courier;
    }
}
