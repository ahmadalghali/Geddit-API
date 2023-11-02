package com.geddit.controller;

import com.geddit.dto.comment.CommentDTO;
import com.geddit.dto.post.PostDTO;
import com.geddit.service.CommentsService;
import com.geddit.service.PostsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments/{commentId}")
public class CommentVotesController {

  private final CommentsService commentsService;

  public CommentVotesController(CommentsService commentsService) {
    this.commentsService = commentsService;
  }

  @PostMapping("/upvote")
  @ResponseStatus(HttpStatus.OK)
  public CommentDTO upvoteComment(@PathVariable String commentId, @RequestHeader("username") String username) {
    return commentsService.upvoteComment(commentId, username);
  }

  @PostMapping("/downvote")
  @ResponseStatus(HttpStatus.OK)
  public CommentDTO downvoteComment(@PathVariable String commentId, @RequestHeader("username") String username) {
    return commentsService.downvoteComment(commentId, username);
  }

  @DeleteMapping("/remove-vote")
  @ResponseStatus(HttpStatus.OK)
  public CommentDTO removeVoteFromComment(@PathVariable String commentId, @RequestHeader("username") String username) {
    return commentsService.removeVoteFromComment(commentId, username);
  }

}
