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
      String postId, String username, CreateCommentDTO createCommentDTO) {
    Post post = postsService.getPostById(postId);
    AppUser author = usersService.getUserByUsername(username);

    Comment comment = new Comment(createCommentDTO.text(), author, post);
    return CommentToDTOConverter.toDTO(commentRepository.save(comment));
  }

  public CommentDTO createReplyToComment(String commentId, CreateCommentDTO createCommentDTO) {
    Comment parentComment = getCommentById(commentId);
    AppUser author = usersService.getUserById(null);

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
    Post post = postsService.getPostById(postId);

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
