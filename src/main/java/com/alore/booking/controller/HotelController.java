package com.alore.booking.controller;

import com.alore.booking.model.*;
import com.alore.booking.service.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("hotel")
@Slf4j
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping("/add")
    public Hotel addHotel(@RequestBody Hotel hotel) {
        return hotelService.updateHotel(hotel, false);
    }

    @PostMapping("/update")
    public Hotel updateHotel(@RequestBody Hotel hotel) {
        return hotelService.updateHotel(hotel, true);
    }

    @GetMapping("/search")
    public List<Hotel> searchHotel(@RequestParam(required = false) String keyterm,
                                   @RequestParam(required = false) City city,
                                   @RequestParam(required = false) Boolean wifi,
                                   @RequestParam(required = false) Boolean restaurant,
                                   @RequestParam(required = false) Boolean airConditioning,
                                   @RequestParam(required = false) Boolean meals,
                                   @RequestParam(required = false) SortBy sortBy) {
        return hotelService.searchHotel(keyterm, city, wifi, restaurant, airConditioning, meals, sortBy);
    }

    @PostMapping("/review/add")
    public void addHotelReview(@RequestBody ReviewPayload reviewPayload) {
        hotelService.addHotelReview(reviewPayload);
    }

    @PostMapping("/review/delete")
    public void deleteReviewsByHotel(@RequestBody ReviewPayload reviewPayload) {
        hotelService.deleteHotelReview(reviewPayload);
    }

    @PostMapping("/{hotelId}/delete")
    public void deleteHotel(@PathVariable Integer hotelId) {
        hotelService.deleteHotel(hotelId);
    }

    @GetMapping("/{hotelId}/get")
    public Hotel getHotel(@PathVariable Integer hotelId) {
        return hotelService.getHotel(hotelId);
    }

    @GetMapping("/{hotelId}/reviews")
    public List<Review> getReviewsByHotel(@PathVariable Integer hotelId, @RequestParam(required = false) Gender gender, @RequestParam(required = false) City city) {
        return hotelService.getReviewsByHotel(hotelId, gender, city);
    }
}
