package com.alore.booking.service;

import com.alore.booking.entity.UserEntity;
import com.alore.booking.exception.ValidationError;
import com.alore.booking.exception.ValidationException;
import com.alore.booking.model.User;
import com.alore.booking.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User updateUser(User user, boolean isModified) {
        try {
            UserEntity newUser = new UserEntity();
            newUser.setCreatedTs(new Timestamp(System.currentTimeMillis()));
            UserEntity existingUser = userRepository.findByEmail(user.getEmail());
            if (isModified && existingUser != null) {
                newUser.setCreatedTs(existingUser.getCreatedTs());
            } else if (!isModified && existingUser != null && existingUser.getIsDeleted() != 1) {
                throw new ValidationException(new ValidationError("Error", "User already Exist"));
            }
            newUser.setEmail(user.getEmail());
            newUser.setGender(user.getGender().name());
            newUser.setCity(user.getCity().name());
            newUser.setLastUpdatedTs(new Timestamp(System.currentTimeMillis()));
            userRepository.save(newUser);
            return user;
        } catch (Exception e) {
            throw new ValidationException(new ValidationError("Error", "User add/update failed: " + e.getMessage()));
        }
    }

    public void deleteUser(String email) {
        UserEntity existingUser = userRepository.findByEmail(email);
        if (existingUser != null) {
            existingUser.setIsDeleted(1);
            existingUser.setLastUpdatedTs(new Timestamp(System.currentTimeMillis()));
            userRepository.save(existingUser);
        } else {
            throw new ValidationException(new ValidationError("404", "Error", "User delete failed"));
        }
    }

    public User getUser(String email) {
        UserEntity existingUser = userRepository.findByEmail(email);
        if (existingUser != null && existingUser.getIsDeleted() != 1) {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(existingUser, User.class);
        } else {
            throw new ValidationException(new ValidationError("404", "Error", "User Not Found"));
        }
    }
}
