package com.tapusd.springbootreactivemongo.service;

import com.tapusd.springbootreactivemongo.domain.Comment;
import com.tapusd.springbootreactivemongo.domain.Post;
import com.tapusd.springbootreactivemongo.domain.User;
import com.tapusd.springbootreactivemongo.dto.CommentDTO;
import com.tapusd.springbootreactivemongo.repository.CommentRepository;
import com.tapusd.springbootreactivemongo.repository.PostRepository;
import com.tapusd.springbootreactivemongo.util.AssertUtil;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
public class CommentServiceImpl implements CommentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentServiceImpl.class);

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserService userService;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, UserService userService) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userService = userService;
    }

    @Override
    public Mono<Comment> createNewComment(CommentDTO dto) {
        AssertUtil.assertNotNull(dto.postId(), "PostId can not be null!");
        AssertUtil.assertNotBlank(dto.username(), "Username");
        AssertUtil.assertNotBlank(dto.content(), "Content");

        return postRepository.findById(dto.postId())
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Post not found with provided id!")))
                .zipWith(userService.findUserByUsername(dto.username()))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("User not found with provided username!")))
                .map(tuple -> {
                    final User user = tuple.getT2();
                    final Post post = tuple.getT1();
                    return new Comment()
                            .setPostId(post.getId())
                            .setUsername(user.getUsername())
                            .setContent(dto.content())
                            .setCreatedAt(Instant.now())
                            .setUpdatedAt(Instant.now());

                })
                .flatMap(commentRepository::save)
                .doOnError(throwable -> LOGGER.error("Error at createNewComment", throwable));
    }

    @Override
    public Flux<Comment> findByPostId(ObjectId postId) {
        return commentRepository.findByPostId(postId);
    }
}
