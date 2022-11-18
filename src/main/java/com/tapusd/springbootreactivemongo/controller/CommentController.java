package com.tapusd.springbootreactivemongo.controller;

import com.tapusd.springbootreactivemongo.domain.Comment;
import com.tapusd.springbootreactivemongo.dto.CommentDTO;
import com.tapusd.springbootreactivemongo.dto.response.ActionResponse;
import com.tapusd.springbootreactivemongo.service.CommentService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public Mono<ActionResponse> createNewComment(@RequestBody CommentDTO dto) {
        try {
            return commentService.createNewComment(dto)
                    .map(comment -> new ActionResponse(true, "Comment created!", comment))
                    .onErrorResume(IllegalArgumentException.class::isInstance, throwable -> Mono.just(new ActionResponse(false, throwable.getMessage(), null)));
        } catch (IllegalArgumentException ex) {
            return Mono.just(new ActionResponse(false, ex.getMessage(), null));
        }
    }

    @GetMapping
    public Flux<Comment> getCommentsByPostId(@RequestParam ObjectId postId) {
        return commentService.findByPostId(postId);
    }
}
