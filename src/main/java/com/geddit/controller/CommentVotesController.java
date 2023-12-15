package com.geddit.controller;

import com.geddit.dto.comment.CommentDTO;
import com.geddit.persistence.entity.AppUser;
import com.geddit.service.CommentsService;
import com.geddit.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments/{commentId}")
public class CommentVotesController {

  private final CommentsService commentsService;
  private final UsersService usersService;

  public CommentVotesController(CommentsService commentsService, UsersService usersService) {
    this.commentsService = commentsService;
    this.usersService = usersService;
  }

  @PostMapping("/upvote")
  @ResponseStatus(HttpStatus.OK)
  public CommentDTO upvoteComment(@PathVariable String commentId, @AuthenticationPrincipal AppUser principal) {
    AppUser user = usersService.getUserById(principal.getId());
    return commentsService.upvoteComment(commentId, user);
  }

  @PostMapping("/downvote")
  @ResponseStatus(HttpStatus.OK)
  public CommentDTO downvoteComment(@PathVariable String commentId, @AuthenticationPrincipal AppUser principal) {
    AppUser user = usersService.getUserById(principal.getId());
    return commentsService.downvoteComment(commentId, user);
  }

  @DeleteMapping("/remove-vote")
  @ResponseStatus(HttpStatus.OK)
  public CommentDTO removeVoteFromComment(@PathVariable String commentId, @AuthenticationPrincipal AppUser principal) {
    AppUser user = usersService.getUserById(principal.getId());
    return commentsService.removeVoteFromComment(commentId, user);
  }

}
