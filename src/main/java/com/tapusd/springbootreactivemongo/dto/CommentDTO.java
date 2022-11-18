package com.tapusd.springbootreactivemongo.dto;

import org.bson.types.ObjectId;

public record CommentDTO(ObjectId postId, String username, String content) {
}
