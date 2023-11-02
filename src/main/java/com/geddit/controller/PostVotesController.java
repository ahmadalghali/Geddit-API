package com.geddit.controller;

import com.geddit.dto.post.PostDTO;
import com.geddit.dto.post.UpdatePostDTO;
import com.geddit.service.PostsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts/{postId}")
public class PostVotesController {

  private final PostsService postsService;

  public PostVotesController(PostsService postsService) {
    this.postsService = postsService;
  }

  @PostMapping("/upvote")
  @ResponseStatus(HttpStatus.OK)
  public PostDTO upvotePost(@PathVariable String postId, @RequestHeader("username") String username) {
    return postsService.upvotePost(postId, username);
  }

  @PostMapping("/downvote")
  @ResponseStatus(HttpStatus.OK)
  public PostDTO downvotePost(@PathVariable String postId, @RequestHeader("username") String username) {
    return postsService.downvotePost(postId, username);
  }

  @DeleteMapping("/remove-vote")
  @ResponseStatus(HttpStatus.OK)
  public PostDTO removeVoteFromPost(@PathVariable String postId, @RequestHeader("username") String username) {
    return postsService.removeVoteFromPost(postId, username);
  }

}
