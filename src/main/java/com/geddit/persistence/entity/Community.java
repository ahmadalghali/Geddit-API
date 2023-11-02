package com.geddit.persistence.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(
    indexes = @Index(name = "index_name", columnList = "name")
)
public class Community {

  @Id
  @Column(nullable = false, unique = true)
  private String name;

  @Column(nullable = false)
  private String description;

  @Column
  private String imageUrl;

  @ManyToOne
  @JoinColumn(nullable = false)
  private AppUser createdBy;

  @CreatedDate
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdDate = LocalDateTime.now();

  @OneToMany(mappedBy = "community")
  private List<Post> posts = new ArrayList<>();

  @ManyToMany
  @JoinTable
  private Set<AppUser> members = new HashSet<>();


  public Community(String name, String description, AppUser createdBy) {
    this.name = name;
    this.description = description;
    this.createdBy = createdBy;
  }

}
