package com.tapusd.springbootreactivemongo.service;

import com.tapusd.springbootreactivemongo.domain.Comment;
import com.tapusd.springbootreactivemongo.dto.CommentDTO;
import org.bson.types.ObjectId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CommentService {
    Mono<Comment> createNewComment(CommentDTO dto);

    Flux<Comment> findByPostId(ObjectId postId);
}
