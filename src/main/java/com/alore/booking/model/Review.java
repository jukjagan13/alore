package com.alore.booking.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Review {
    private Hotel hotel;
    private User user;
    private Integer rating;
    private String comments;
    private Timestamp reviewedTs;
}
