package com.geddit.service;

import com.geddit.converter.CommentToDTOConverter;
import com.geddit.dto.comment.CommentDTO;
import com.geddit.dto.comment.CreateCommentDTO;
import com.geddit.dto.comment.UpdateCommentDTO;
import com.geddit.exceptions.CommentNotFoundException;
import com.geddit.persistence.entity.AppUser;
import com.geddit.persistence.entity.Comment;
import com.geddit.persistence.entity.Post;
import com.geddit.persistence.repository.CommentRepository;
import com.geddit.persistence.repository.UserRepository;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
  private final CommentRepository commentRepository;
  private final PostService postService;
  private final UserService userService;

  public CommentService(
      CommentRepository commentRepository,
      UserRepository userRepository,
      PostService postService,
      UserService userService) {
    this.commentRepository = commentRepository;
    this.postService = postService;
    this.userService = userService;
  }

  public CommentDTO createComment(
      String postId, String username, CreateCommentDTO createCommentDTO) {
    Post post = postService.getPostById(postId);
    AppUser author = userService.getUserByUsername(username);

    Comment comment = new Comment(createCommentDTO.text(), author, post);
    return CommentToDTOConverter.toDTO(commentRepository.save(comment));
  }

  public CommentDTO createReply(
      String communityName, String postId, String commentId, CreateCommentDTO createCommentDTO) {
    Comment parentComment = getCommentById(commentId);
    AppUser author = userService.getUserById(null);

    Comment reply = new Comment(createCommentDTO.text(), author, parentComment.getPost());

    reply.setParentComment(parentComment);

    return CommentToDTOConverter.toDTO(commentRepository.save(reply));
  }

  private Comment getCommentById(String commentId) {
    return commentRepository
        .findById(commentId)
        .orElseThrow(() -> new CommentNotFoundException(commentId));
  }

  public Set<Comment> getCommentsByPostId(String postId) {
    Post post = postService.getPostById(postId);

    Set<Comment> postComments = post.getComments();
    return postComments;
  }

  public List<Comment> getUserComments(String username) {
    List<Comment> allByAuthorUsername = commentRepository.findAllByAuthorUsername(username);
    return allByAuthorUsername;
  }

  public void deleteComment(String commentId) {
    commentRepository.deleteById(commentId);
  }

  public CommentDTO patchUpdateComment(String commentId, UpdateCommentDTO updateCommentDTO) {
    Comment comment = getCommentById(commentId);

    if (updateCommentDTO.text() != null) {
      comment.setText(updateCommentDTO.text());
    }

    return CommentToDTOConverter.toDTO(commentRepository.save(comment));
  }
}
