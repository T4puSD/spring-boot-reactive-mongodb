package com.tapusd.springbootreactivemongo.service;

import com.tapusd.springbootreactivemongo.domain.User;
import com.tapusd.springbootreactivemongo.dto.CreateUserDTO;
import com.tapusd.springbootreactivemongo.repository.UserRepository;
import com.tapusd.springbootreactivemongo.util.AssertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Mono<User> createNewUser(CreateUserDTO dto) {
        AssertUtil.assertNotBlank(dto.email(), "Email");
        AssertUtil.assertNotBlank(dto.username(), "Username");

        var user = new User()
                .setEmail(dto.email())
                .setUsername(dto.username())
                .setCreatedAt(Instant.now())
                .setUpdatedAt(Instant.now());

        return userRepository.save(user)
                .doOnError(throwable -> LOGGER.error("Error during creating new user!", throwable));
    }
}
