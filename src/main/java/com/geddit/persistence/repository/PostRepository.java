package com.geddit.persistence.repository;

import com.geddit.persistence.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, String> {

    @Query("""
                SELECT p FROM Post p
                ORDER BY p.createdDate DESC
                LIMIT 20
            """)
    List<Post> findMostRecentPostsLimit20();

    @Query("""
              SELECT p FROM Post p
              WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """)
    List<Post> findAllByTitleContainingIgnoreCase(String keyword);

    @Query("""
                SELECT p FROM Post p
                LEFT JOIN p.community c
                WHERE c.name = :communityName
                ORDER BY p.createdDate DESC
            """)
    List<Post> findAllPostsByCommunityName(String communityName);

    @Query("""
                SELECT p FROM Post p
                LEFT JOIN p.author author
                WHERE author.username = :username
                ORDER BY p.createdDate DESC
            """)
    List<Post> findAllByUsername(String username);
}
