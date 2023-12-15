package com.geddit.controller;

import com.geddit.dto.comment.CommentDTO;
import com.geddit.dto.comment.CreateCommentDTO;
import com.geddit.persistence.entity.AppUser;
import com.geddit.service.CommentsService;
import com.geddit.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments/{commentId}/replies")
public class CommentRepliesController {

  private final CommentsService commentsService;
  private final UsersService usersService;
  public CommentRepliesController(CommentsService commentsService, UsersService usersService) {
    this.commentsService = commentsService;
    this.usersService = usersService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CommentDTO createReply(
          @PathVariable String commentId,
          @Valid @RequestBody CreateCommentDTO createCommentDto,
          @AuthenticationPrincipal AppUser principal) {
    AppUser user = usersService.getUserById(principal.getId());
    return commentsService.createReplyToComment(commentId, user, createCommentDto);
  }

}
