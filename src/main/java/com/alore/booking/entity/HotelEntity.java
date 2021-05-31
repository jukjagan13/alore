package com.alore.booking.entity;

import com.alore.booking.model.City;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "hotels")
@Data
public class HotelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer hotelId;
    private String hotelName;
    private String city;
    private Integer totalRooms;
    private Integer costPerRoom;
    private Integer wifiAvailable;
    private Integer acAvailable;
    private Integer restaurantAvailable;
    private Integer mealsIncluded;
    private Integer isRelevance=0;
    private Integer isDeleted=0;
    private Timestamp lastUpdatedTs;
    private Timestamp createdTs;
}
