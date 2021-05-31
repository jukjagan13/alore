package com.alore.booking.repository;

import com.alore.booking.entity.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<HotelEntity, Integer> {
    HotelEntity findByHotelId(Integer hotelId);

    @Query(value = "FROM HotelEntity where (hotelName like %?1% or city like %?1%) and isDeleted=0")
    List<HotelEntity> getAllByHotelName(String hotelName);
}
