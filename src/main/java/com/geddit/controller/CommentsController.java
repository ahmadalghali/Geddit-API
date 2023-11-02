package com.geddit.controller;

import com.geddit.dto.comment.CommentDTO;
import com.geddit.dto.comment.CreateCommentDTO;
import com.geddit.dto.comment.UpdateCommentDTO;
import com.geddit.service.CommentsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentsController {

  private final CommentsService commentsService;

  public CommentsController(CommentsService commentsService) {
    this.commentsService = commentsService;
  }

  @DeleteMapping("/{commentId}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteComment(@PathVariable String commentId) {
    commentsService.deleteComment(commentId);
  }

  @PatchMapping("/{commentId}")
  @ResponseStatus(HttpStatus.OK)
  public CommentDTO patchUpdateComment(@PathVariable String commentId, @RequestBody UpdateCommentDTO updateCommentDTO) {
    return commentsService.patchUpdateComment(commentId, updateCommentDTO);
  }

}

