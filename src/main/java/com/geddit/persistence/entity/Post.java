package com.geddit.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@SQLDelete(sql = "UPDATE post SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class Post {

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private String id;

  @Column(nullable = false)
  private String title;

  @Column(length = 10_000)
  private String body;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Community community;

  @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
  Set<Comment> comments = new HashSet<>();

  @ManyToOne
  @JoinColumn(nullable = false)
  private AppUser author;

  @ManyToMany
  Set<AppUser> upvotedBy = new HashSet<>();

  @ManyToMany
  Set<AppUser> downvotedBy = new HashSet<>();

  @CreatedDate
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Instant createdDate = Instant.now();

  private boolean deleted = false;

  public Post(String title, Community community, AppUser author) {
    this.title = title;
    this.community = community;
    this.author = author;
  }

  public Post(String title, Community community, AppUser author, String body) {
    this.title = title;
    this.community = community;
    this.author = author;
    this.body = body;
  }
}
