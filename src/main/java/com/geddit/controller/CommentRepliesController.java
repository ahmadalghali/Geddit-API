package com.geddit.controller;

import com.geddit.converter.CommentToDTOConverter;
import com.geddit.dto.comment.CommentDTO;
import com.geddit.dto.comment.CreateCommentDTO;
import com.geddit.service.CommentsService;
import com.geddit.service.PostsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/comments/{commentId}/replies")
public class CommentRepliesController {

  private final CommentsService commentsService;

  public CommentRepliesController(CommentsService commentsService) {
    this.commentsService = commentsService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CommentDTO createReply(
          @PathVariable String commentId,
          @Valid @RequestBody CreateCommentDTO createCommentDto,
          @RequestHeader("username") String username) {
    return commentsService.createReplyToComment(commentId, username, createCommentDto);
  }

}
