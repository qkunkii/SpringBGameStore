package com.qkunkii.qkn.repo;

import org.springframework.data.repository.CrudRepository;
import com.qkunkii.qkn.models.Post;

public interface PostRepository extends CrudRepository<Post, Long> {

}
