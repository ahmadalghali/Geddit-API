package com.geddit.controller;

import com.geddit.converter.CommentToDTOConverter;
import com.geddit.dto.comment.CommentDTO;
import com.geddit.dto.comment.CreateCommentDTO;
import com.geddit.persistence.entity.AppUser;
import com.geddit.service.AuthService;
import com.geddit.service.CommentsService;
import com.geddit.service.PostsService;
import com.geddit.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/posts/{postId}/comments")
public class PostCommentsController {

  private final PostsService postsService;
  private final CommentsService commentsService;
  private final UsersService usersService;
  private final AuthService authService;

  public PostCommentsController(PostsService postsService, CommentsService commentsService, UsersService usersService, AuthService authService) {
    this.postsService = postsService;
    this.commentsService = commentsService;
    this.usersService = usersService;
    this.authService = authService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CommentDTO createComment(
          @PathVariable String postId,
          @Valid @RequestBody CreateCommentDTO createCommentDto,
          @AuthenticationPrincipal AppUser principal
          ) {

    AppUser user = usersService.getUserById(principal.getId());

    return commentsService.createComment(postId, user, createCommentDto);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Set<CommentDTO> getCommentsByPostId(@PathVariable String postId) {
    return CommentToDTOConverter.toDTOSet(commentsService.getCommentsByPostId(postId), authService.getCurrentUser());
  }

}
