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
import java.util.Optional;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE comment SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class Comment {

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private String id;

  @Column(length = 10_000, nullable = false)
  private String text;

  @ManyToOne
  @JoinColumn(nullable = false)
  private AppUser author;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Post post;

  @ManyToOne()
  private Comment parentComment;

  @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
  private Set<Comment> replies = new HashSet<>();

  @ManyToMany
  Set<AppUser> upvotedBy = new HashSet<>();

  @ManyToMany
  Set<AppUser> downvotedBy = new HashSet<>();

  @CreatedDate
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Instant createdDate = Instant.now();

  private boolean deleted = false;

  public Comment(String text, AppUser author, Post post) {
    this.text = text;
    this.author = author;
    this.post = post;
  }

  public Optional<Comment> getParentComment() {
    return Optional.ofNullable(parentComment);
  }
}
