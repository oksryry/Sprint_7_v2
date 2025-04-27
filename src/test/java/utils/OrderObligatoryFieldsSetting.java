package utils;

import entities.Order;

public class OrderObligatoryFieldsSetting {
    public Order setObligatoryFields() {
        Order order = new Order(UtilsRandomString.randomString(), UtilsRandomString.randomString(), UtilsRandomString.randomString(), UtilsRandomString.randomString(), UtilsRandomString.randomString(), UtilsRandomString.numberOfDays(), UtilsRandomString.randomString(), UtilsRandomString.randomString());
        return order;
    }
}
