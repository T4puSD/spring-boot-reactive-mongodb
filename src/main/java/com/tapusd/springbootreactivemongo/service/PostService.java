package com.tapusd.springbootreactivemongo.service;

import com.tapusd.springbootreactivemongo.domain.Post;
import com.tapusd.springbootreactivemongo.dto.CreatePostDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostService {
    Mono<Post> createNewPost(CreatePostDTO dto);

    Flux<Post> findAll();
}
