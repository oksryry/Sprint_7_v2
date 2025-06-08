package entities;

import lombok.Getter;
import lombok.Setter;

public class Order {

    private String firstName;
    private String lastName;
    private String address;
    private String metroStation; //возможно тип должен быть int
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
//    @Getter @Setter
    private String[] color;

    public String[] getColor() {
        return color;
    }

    public Order setColor(String[] color) {
        this.color = color;
        return this;
    }

    public Order(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;

    }
}
