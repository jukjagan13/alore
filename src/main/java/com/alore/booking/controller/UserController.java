package com.alore.booking.controller;

import com.alore.booking.model.User;
import com.alore.booking.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public User addUser(@RequestBody User user) {
        return userService.updateUser(user, false);
    }

    @PostMapping("/update")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user, true);
    }

    @PostMapping("/{email}/delete")
    public void deleteUser(@PathVariable String email) {
        userService.deleteUser(email);
    }

    @PostMapping("/{email}/get")
    public User getUser(@PathVariable String email) {
        return userService.getUser(email);
    }
}
