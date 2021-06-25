package com.codegym.reddit.repository;

import com.codegym.reddit.model.Comment;
import com.codegym.reddit.model.Post;
import com.codegym.reddit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}
