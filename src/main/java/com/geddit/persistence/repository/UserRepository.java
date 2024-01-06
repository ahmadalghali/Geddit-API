package com.geddit.persistence.repository;

import com.geddit.persistence.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, String> {
//  Optional<AppUser> findAppUserByEmail(String email);

//  Optional<AppUser> findAppUserByEmail(String email);
//  Optional<AppUser> findByEmail(String email);

  @Query(
      """
                SELECT u FROM AppUser u
                WHERE LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
                GROUP BY u
              """)
  List<AppUser> findAllByEmailContainingIgnoreCase(String keyword);

  Optional<AppUser> findByEmail(String email);
}
