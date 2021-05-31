package com.alore.booking.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "hotel_review")
@Data
@IdClass(ReviewIdClass.class)
public class HotelReviewEntity {
    @Id
    private Integer hotelId;
    @Id
    private String reviewerEmail;
    private Integer rating;
    private String comments;
    private Integer isDeleted=0;
    private Timestamp reviewedTs;
}
