package com.alore.booking.model;

import lombok.Data;

@Data
public class ReviewPayload {
    private Integer hotelId;
    private String email;
    private Integer rating;
    private String comments;
}
