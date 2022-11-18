package com.tapusd.springbootreactivemongo.controller;

import com.tapusd.springbootreactivemongo.domain.Post;
import com.tapusd.springbootreactivemongo.dto.CreatePostDTO;
import com.tapusd.springbootreactivemongo.dto.response.ActionResponse;
import com.tapusd.springbootreactivemongo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public Mono<ActionResponse> createNewPost(@RequestBody CreatePostDTO dto) {
        try {
            return postService.createNewPost(dto)
                    .map(post -> new ActionResponse(true, "Post created!", post))
                    .onErrorResume(IllegalArgumentException.class::isInstance, throwable -> Mono.just(new ActionResponse(false, throwable.getMessage(), null)));
        } catch (IllegalArgumentException ex) {
            return Mono.just(new ActionResponse(false, ex.getMessage(), null));
        }
    }

    @GetMapping
    public Flux<Post> getAllPosts() {
        return postService.findAll();
    }
}
