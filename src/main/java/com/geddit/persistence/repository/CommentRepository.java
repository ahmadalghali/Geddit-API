package com.geddit.persistence.repository;

import com.geddit.persistence.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, String> {
//  @Query("""
//    SELECT new com.geddit.dto.comment.CommentDTO(c.id, c.text, a.username, p.id, c.createdDate)
//    FROM Comment c
//    LEFT JOIN c.author a
//    LEFT JOIN c.post p
//    WHERE a.username = :username
//    ORDER BY c.createdDate DESC
//""")
  List<Comment> findAllByAuthorUsername(String username);
}