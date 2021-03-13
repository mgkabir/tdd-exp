package io.kabir.validationexample.model;

import javax.validation.constraints.NotBlank;

public class Address {
    @NotBlank(message = "City is mandatory.")
    private String city;
    @NotBlank(message = "State is mandatory.")
    private String state;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
