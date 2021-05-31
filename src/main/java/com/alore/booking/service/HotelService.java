package com.alore.booking.service;

import com.alore.booking.entity.HotelEntity;
import com.alore.booking.entity.HotelReviewEntity;
import com.alore.booking.entity.UserEntity;
import com.alore.booking.exception.ValidationError;
import com.alore.booking.exception.ValidationException;
import com.alore.booking.model.*;
import com.alore.booking.repository.HotelRepository;
import com.alore.booking.repository.HotelReviewRepository;
import com.alore.booking.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelReviewRepository hotelReviewRepository;

    public Hotel updateHotel(Hotel hotel, boolean isModified) {
        try {
            HotelEntity newHotel = new ModelMapper().map(hotel, HotelEntity.class);
            newHotel.setCreatedTs(new Timestamp(System.currentTimeMillis()));
            if (isModified && hotel.getHotelId() != null) {
                HotelEntity existingHotel = hotelRepository.findByHotelId(hotel.getHotelId());
                if (existingHotel != null) {
                    newHotel.setHotelId(existingHotel.getHotelId());
                    newHotel.setCreatedTs(existingHotel.getCreatedTs());
                }
            }
            newHotel.setLastUpdatedTs(new Timestamp(System.currentTimeMillis()));
            newHotel = hotelRepository.save(newHotel);
            hotel.setHotelId(newHotel.getHotelId());
            return hotel;
        } catch (Exception e) {
            throw new ValidationException(new ValidationError("Error", "Hotel add/update failed: " + e.getMessage()));
        }
    }

    public void deleteHotel(Integer hotelId) {
        HotelEntity existingHotel = hotelRepository.findByHotelId(hotelId);
        if (existingHotel != null) {
            existingHotel.setIsDeleted(1);
            existingHotel.setLastUpdatedTs(new Timestamp(System.currentTimeMillis()));
            hotelRepository.save(existingHotel);
        } else {
            throw new ValidationException(new ValidationError("404", "Error", "Hotel delete failed"));
        }
    }

    public void addHotelReview(ReviewPayload payload) {
        HotelEntity existingHotel = hotelRepository.findByHotelId(payload.getHotelId());
        UserEntity existingUser = userRepository.findByEmail(payload.getEmail());
        if (existingHotel != null && existingUser != null) {
            HotelReviewEntity existingReview = hotelReviewRepository.findByHotelIdAndReviewerEmail(payload.getHotelId(), payload.getEmail());
            //Restricting user to review hotel one time.
            if (existingReview == null) {
                HotelReviewEntity newReview = new HotelReviewEntity();
                newReview.setHotelId(payload.getHotelId());
                newReview.setReviewerEmail(payload.getEmail());
                newReview.setRating(payload.getRating());
                newReview.setComments(payload.getComments());
                newReview.setReviewedTs(new Timestamp(System.currentTimeMillis()));
                hotelReviewRepository.save(newReview);
            } else {
                throw new ValidationException(new ValidationError("404", "Error", "Already reviewed"));
            }
        } else {
            throw new ValidationException(new ValidationError("404", "Error", "Hotel review failed"));
        }
    }

    public void deleteHotelReview(ReviewPayload reviewPayload) {
        HotelReviewEntity existingReview = hotelReviewRepository.findByHotelIdAndReviewerEmail(reviewPayload.getHotelId(), reviewPayload.getEmail());
        if (existingReview != null) {
            existingReview.setIsDeleted(1);
            hotelReviewRepository.save(existingReview);
        } else {
            throw new ValidationException(new ValidationError("404", "Error", "Hotel review failed"));
        }
    }

    public Hotel getHotel(Integer hotelId) {
        HotelEntity existingHotel = hotelRepository.findByHotelId(hotelId);
        if (existingHotel != null && existingHotel.getIsDeleted() != 1) {
            Hotel hotel = new ModelMapper().map(existingHotel, Hotel.class);
            List<HotelReviewEntity> existingReviews = hotelReviewRepository.findAllByHotelId(hotelId);
            if (existingReviews.size() > 0) {
                Integer totalRatings = existingReviews.stream().mapToInt(HotelReviewEntity::getRating).sum();
                if (totalRatings != 0)
                    hotel.setAvgRating(Math.round((totalRatings.doubleValue() / existingReviews.size()) * 100.0) / 100.0);
            }
            return hotel;
        } else {
            throw new ValidationException(new ValidationError("404", "Error", "Hotel Not Found"));
        }
    }

    public List<Review> getReviewsByHotel(Integer hotelId, Gender gender, City city) {
        ModelMapper modelMapper = new ModelMapper();
        HotelEntity existingHotel = hotelRepository.findByHotelId(hotelId);
        if (existingHotel != null && existingHotel.getIsDeleted() != 1) {
            List<HotelReviewEntity> existingReviews = hotelReviewRepository.findAllByHotelId(hotelId);
            if (existingReviews.size() > 0) {
                List<Review> reviews = new ArrayList<>();
                Hotel hotel = modelMapper.map(existingHotel, Hotel.class);
                existingReviews.forEach(r -> {
                    Review review = new Review();
                    review.setHotel(hotel);
                    UserEntity userEntity = userRepository.findByEmail(r.getReviewerEmail());
                    if (userEntity != null) {
                        review.setUser(modelMapper.map(userEntity, User.class));
                    }
                    review.setRating(r.getRating());
                    review.setComments(r.getComments());
                    review.setReviewedTs(r.getReviewedTs());
                    if (review.getUser() != null && (gender == null || (review.getUser().getGender().equals(gender))) && (city == null || (review.getUser().getCity().equals(city)))) {
                        reviews.add(review);
                    }
                });

                return reviews;
            }
        } else {
            throw new ValidationException(new ValidationError("404", "Error", "Hotel Not Found"));
        }
        return new ArrayList<>();
    }

    public List<Hotel> searchHotel(String keyterm, City city, Boolean wifi, Boolean restaurant, Boolean airConditioning, Boolean meals, SortBy sortBy) {
        if (keyterm == null)
            keyterm = "";

        List<Hotel> hotels = new ArrayList<>();

        List<HotelEntity> hotelEntityList = hotelRepository.getAllByHotelName(keyterm);
        if (hotelEntityList.size() > 0) {
            hotels = hotelEntityList.stream().map(h -> getHotel(h.getHotelId())).collect(Collectors.toList());

            if (city != null) {
                hotels = hotels.stream().filter(h -> h.getCity().equals(city)).collect(Collectors.toList());
            }

            if (wifi != null && wifi) {
                hotels = hotels.stream().filter(h -> h.getWifiAvailable().equals(1)).collect(Collectors.toList());
            }

            if (restaurant != null && restaurant) {
                hotels = hotels.stream().filter(h -> h.getRestaurantAvailable().equals(1)).collect(Collectors.toList());
            }

            if (airConditioning != null && airConditioning) {
                hotels = hotels.stream().filter(h -> h.getAcAvailable().equals(1)).collect(Collectors.toList());
            }

            if (meals != null && meals) {
                hotels = hotels.stream().filter(h -> h.getMealsIncluded().equals(1)).collect(Collectors.toList());
            }

            if (sortBy != null && sortBy.equals(SortBy.Cost)) {
                hotels.sort(Comparator.comparing(Hotel::getCostPerRoom));
            } else if (sortBy != null && sortBy.equals(SortBy.Rating)) {
                hotels.sort(Comparator.comparing(Hotel::getAvgRating));
            } else if (sortBy != null && sortBy.equals(SortBy.Relevance)) {
                hotels.sort(Comparator.comparing(Hotel::getIsRelevance));
            }
        }
        return hotels;
    }
}
