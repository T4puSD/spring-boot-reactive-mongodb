package com.tapusd.springbootreactivemongo.controller;

import com.tapusd.springbootreactivemongo.domain.User;
import com.tapusd.springbootreactivemongo.dto.CreateUserDTO;
import com.tapusd.springbootreactivemongo.dto.response.ActionResponse;
import com.tapusd.springbootreactivemongo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public Mono<ActionResponse> createNewUser(@RequestBody CreateUserDTO dto) {
        try {
            return userService.createNewUser(dto)
                    .map(user -> new ActionResponse(true, "User created!", user));
        } catch (IllegalArgumentException ex) {
            return Mono.just(new ActionResponse(false, ex.getMessage(), null));
        }
    }
}
