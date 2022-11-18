package com.tapusd.springbootreactivemongo.service;

import com.tapusd.springbootreactivemongo.domain.Post;
import com.tapusd.springbootreactivemongo.dto.CreatePostDTO;
import com.tapusd.springbootreactivemongo.repository.PostRepository;
import com.tapusd.springbootreactivemongo.util.AssertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
public class PostServiceImpl implements PostService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostServiceImpl.class);

    private final UserService userService;
    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(UserService userService, PostRepository postRepository) {
        this.userService = userService;
        this.postRepository = postRepository;
    }

    @Override
    public Mono<Post> createNewPost(CreatePostDTO dto) {
        AssertUtil.assertNotBlank(dto.username(), "Username");
        AssertUtil.assertNotBlank(dto.content(), "Content");

        return userService.findUserByUsername(dto.username())
                .map(user -> new Post()
                        .setContent(dto.content())
                        .setUsername(user.getUsername())
                        .setCreatedAt(Instant.now())
                        .setUpdatedAt(Instant.now()))
                .flatMap(postRepository::save)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("User not found with provided username")))
                .doOnError(throwable -> LOGGER.error("Error occurred during saving new post", throwable));
    }

    @Override
    public Flux<Post> findAll() {
        return postRepository.findAll()
                .doOnError(throwable -> LOGGER.error("Error at findAll", throwable));
    }
}
