package com.alore.booking.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReviewIdClass implements Serializable {
    private Integer hotelId;
    private String reviewerEmail;
}
