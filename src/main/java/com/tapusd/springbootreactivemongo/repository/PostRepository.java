package com.tapusd.springbootreactivemongo.repository;

import com.tapusd.springbootreactivemongo.domain.Post;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends ReactiveMongoRepository<Post, ObjectId> {
}
