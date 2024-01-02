package com.geddit.persistence.repository;

import com.geddit.persistence.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommunityRepository extends JpaRepository<Community, String> {

    // TODO costly?
    @Query("""
              SELECT c FROM Community c
              WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """)
    List<Community> findAllByNameContainingIgnoreCase(String keyword);

    boolean existsByNameIgnoreCase(String name);

    @Query("""
               SELECT c FROM Community c
               WHERE c.name = :name
            """)
    Optional<Community> findCommunitySummaryByName(String name);

    Optional<Community> findCommunityByName(String name);

}
