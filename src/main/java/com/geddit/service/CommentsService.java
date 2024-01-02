package com.geddit.service;

import com.geddit.converter.CommentToDTOConverter;
import com.geddit.dto.comment.CommentDTO;
import com.geddit.dto.comment.CreateCommentDTO;
import com.geddit.dto.comment.UpdateCommentDTO;
import com.geddit.exceptions.CommentNotFoundException;
import com.geddit.exceptions.GedditException;
import com.geddit.persistence.entity.AppUser;
import com.geddit.persistence.entity.Comment;
import com.geddit.persistence.entity.Post;
import com.geddit.persistence.repository.CommentRepository;
import com.geddit.persistence.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class CommentsService {
  private final CommentRepository commentRepository;
  private final PostsService postsService;
  private final UsersService usersService;

  public CommentsService(
      CommentRepository commentRepository,
      UserRepository userRepository,
      PostsService postsService,
      UsersService usersService) {
    this.commentRepository = commentRepository;
    this.postsService = postsService;
    this.usersService = usersService;
  }

  public CommentDTO createComment(
          String postId, AppUser user, CreateCommentDTO createCommentDTO) {
    Post post = postsService.getPostById(postId);

    Comment comment = new Comment(createCommentDTO.text(), user, post);
    return CommentToDTOConverter.toDTO(commentRepository.save(comment), Optional.of(user));
  }

  public CommentDTO createReplyToComment(String commentId, AppUser user, CreateCommentDTO createCommentDTO) {
    Comment parentComment = getCommentById(commentId);

    Comment reply = new Comment(createCommentDTO.text(), user, parentComment.getPost());

    reply.setParentComment(parentComment);

    return CommentToDTOConverter.toDTO(commentRepository.save(reply), Optional.of(user));
  }

  private Comment getCommentById(String commentId) {
    return commentRepository
        .findById(commentId)
        .orElseThrow(() -> new CommentNotFoundException(commentId));
  }

  public Set<Comment> getCommentsByPostId(String postId) {
    Post post = postsService.getPostById(postId);

    Set<Comment> postComments = post.getComments();
    return postComments;
  }

  public Set<CommentDTO> getUserComments(String username) {
    List<Comment> allByAuthorUsername = commentRepository.findAllByAuthorEmail(username);
    Optional<AppUser> userOptional = usersService.getUserOptionalByEmail(username);
    return CommentToDTOConverter.toDTOSet(allByAuthorUsername, userOptional);
  }

  public void deleteComment(String commentId, AppUser user) {
    Comment comment = getCommentById(commentId);

    if (!comment.getAuthor().getId().equals(user.getId())) {
      throw new GedditException("You are unauthorized to delete this comment as you are not the author.");
    }

    commentRepository.deleteById(commentId);
  }

  public CommentDTO patchUpdateComment(String commentId, UpdateCommentDTO updateCommentDTO, AppUser user) {
    Comment comment = getCommentById(commentId);

    if (!comment.getAuthor().getId().equals(user.getId())) {
      throw new GedditException("You are unauthorized to update this comment as you are not the author");
    }
    if (updateCommentDTO.text() != null) {
      comment.setText(updateCommentDTO.text());
    }

    return CommentToDTOConverter.toDTO(commentRepository.save(comment), Optional.of(user));
  }

  public CommentDTO upvoteComment(String commentId, AppUser user) {
    Comment comment = getCommentById(commentId);

    if (comment.getDownvotedBy().contains(user)) {
      comment.getDownvotedBy().remove(user);
    }

    comment.getUpvotedBy().add(user);

    return CommentToDTOConverter.toDTO(commentRepository.save(comment), Optional.of(user));
  }

  public CommentDTO downvoteComment(String commentId, AppUser user) {
    Comment comment = getCommentById(commentId);

    if (comment.getUpvotedBy().contains(user)) {
      comment.getUpvotedBy().remove(user);
    }

    comment.getDownvotedBy().add(user);
    return CommentToDTOConverter.toDTO(commentRepository.save(comment), Optional.of(user));

  }

  public CommentDTO removeVoteFromComment(String commentId, AppUser user) {
    Comment comment = getCommentById(commentId);

    if (comment.getUpvotedBy().contains(user)) {
      comment.getUpvotedBy().remove(user);
    }

    if (comment.getDownvotedBy().contains(user)) {
      comment.getDownvotedBy().remove(user);
    }

    return CommentToDTOConverter.toDTO(commentRepository.save(comment), Optional.of(user));
  }

}
