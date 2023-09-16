package com.geddit.controller;

import com.geddit.converter.CommentToDTOConverter;
import com.geddit.dto.comment.CommentDTO;
import com.geddit.dto.post.PostDTO;
import com.geddit.dto.post.UpdatePostDTO;
import com.geddit.service.PostsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/posts")
public class PostsController {

  private final PostsService postsService;

  public PostsController(PostsService postsService) {
    this.postsService = postsService;
  }

  @GetMapping("/{postId}")
  @ResponseStatus(HttpStatus.OK)
  public PostDTO getPostById(@PathVariable String postId) {
    return postsService.getPostDTOById(postId);
  }

  @DeleteMapping("/{postId}")
  @ResponseStatus(HttpStatus.OK)
  public void deletePost(@PathVariable String postId) {
    postsService.deletePost(postId);
  }

  @PatchMapping("/{postId}")
  @ResponseStatus(HttpStatus.OK)
  public PostDTO updatePost(@PathVariable String postId, @RequestBody UpdatePostDTO updatePostDTO) {
    return postsService.updatePost(postId, updatePostDTO);
  }

}
