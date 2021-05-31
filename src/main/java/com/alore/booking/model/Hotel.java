package com.alore.booking.model;

import lombok.Data;

@Data
public class Hotel {
    private Integer hotelId;
    private String hotelName;
    private City city;
    private Integer totalRooms;
    private Integer costPerRoom;
    private Integer wifiAvailable;
    private Integer acAvailable;
    private Integer restaurantAvailable;
    private Integer mealsIncluded;
    private Double avgRating;
    private Integer isRelevance;
}
