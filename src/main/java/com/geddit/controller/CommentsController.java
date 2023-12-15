package com.geddit.controller;

import com.geddit.dto.comment.CommentDTO;
import com.geddit.dto.comment.UpdateCommentDTO;
import com.geddit.persistence.entity.AppUser;
import com.geddit.service.CommentsService;
import com.geddit.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentsController {

  private final CommentsService commentsService;
  private final UsersService usersService;

  public CommentsController(CommentsService commentsService, UsersService usersService) {
    this.commentsService = commentsService;
    this.usersService = usersService;
  }

  @DeleteMapping("/{commentId}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteComment(@PathVariable String commentId, @AuthenticationPrincipal AppUser principal) {
    AppUser user = usersService.getUserById(principal.getId());

    commentsService.deleteComment(commentId, user);
  }

  @PatchMapping("/{commentId}")
  @ResponseStatus(HttpStatus.OK)
  public CommentDTO patchUpdateComment(@PathVariable String commentId, @RequestBody UpdateCommentDTO updateCommentDTO, @AuthenticationPrincipal AppUser principal) {
    AppUser user = usersService.getUserById(principal.getId());
    return commentsService.patchUpdateComment(commentId, updateCommentDTO, user);
  }

}

