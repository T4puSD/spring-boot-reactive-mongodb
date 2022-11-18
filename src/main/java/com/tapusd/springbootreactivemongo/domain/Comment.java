package com.tapusd.springbootreactivemongo.domain;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;

@Document
public class Comment {

    @MongoId
    private ObjectId id;

    private ObjectId postId;

    private String content;

    private String username;

    private Instant createdAt;

    private Instant updatedAt;

    public ObjectId getId() {
        return id;
    }

    public Comment setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public ObjectId getPostId() {
        return postId;
    }

    public Comment setPostId(ObjectId postId) {
        this.postId = postId;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Comment setContent(String content) {
        this.content = content;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Comment setUsername(String username) {
        this.username = username;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Comment setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Comment setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}