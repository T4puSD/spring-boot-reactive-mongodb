package com.tapusd.springbootreactivemongo.service;

import com.tapusd.springbootreactivemongo.domain.User;
import com.tapusd.springbootreactivemongo.dto.CreateUserDTO;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> findUserByUsername(String username);

    Mono<User> createNewUser(CreateUserDTO dto);
}
