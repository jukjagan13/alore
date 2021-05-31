package com.alore.booking.repository;

import com.alore.booking.entity.HotelEntity;
import com.alore.booking.entity.HotelReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelReviewRepository extends JpaRepository<HotelReviewEntity, Integer> {
    List<HotelReviewEntity> findAllByHotelId(Integer hotelId);

    List<HotelReviewEntity> findAllByReviewerEmail(String email);

    HotelReviewEntity findByHotelIdAndReviewerEmail(Integer hotelId, String email);
}
