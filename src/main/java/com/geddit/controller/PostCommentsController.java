package com.geddit.controller;

import com.geddit.converter.CommentToDTOConverter;
import com.geddit.dto.comment.CommentDTO;
import com.geddit.dto.comment.CreateCommentDTO;
import com.geddit.service.CommentsService;
import com.geddit.service.PostsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/posts/{postId}/comments")
public class PostCommentsController {

  private final PostsService postsService;
  private final CommentsService commentsService;

  public PostCommentsController(PostsService postsService, CommentsService commentsService) {
    this.postsService = postsService;
    this.commentsService = commentsService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CommentDTO createComment(
          @PathVariable String postId,
          @RequestBody CreateCommentDTO createCommentDto,
          @RequestHeader("username") String username) {
    return commentsService.createComment(postId, username, createCommentDto);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Set<CommentDTO> getCommentsByPostId(@PathVariable String postId) {
    return CommentToDTOConverter.toDTOSet(commentsService.getCommentsByPostId(postId));
  }

}
