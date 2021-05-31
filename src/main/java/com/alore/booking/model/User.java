package com.alore.booking.model;

import lombok.Data;

@Data
public class User {
    private String email;
    private Gender gender;
    private City city;
}
