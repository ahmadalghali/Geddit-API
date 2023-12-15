package com.geddit.controller;

import com.geddit.dto.post.PostDTO;
import com.geddit.persistence.entity.AppUser;
import com.geddit.service.PostsService;
import com.geddit.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts/{postId}")
public class PostVotesController {

  private final PostsService postsService;
  private final UsersService usersService;

  public PostVotesController(PostsService postsService, UsersService usersService) {
    this.postsService = postsService;
    this.usersService = usersService;
  }

  @PostMapping("/upvote")
  @ResponseStatus(HttpStatus.OK)
  public PostDTO upvotePost(@PathVariable String postId, @AuthenticationPrincipal AppUser principal) {
    AppUser user = usersService.getUserById(principal.getId());

    return postsService.upvotePost(postId, user);
  }

  @PostMapping("/downvote")
  @ResponseStatus(HttpStatus.OK)
  public PostDTO downvotePost(@PathVariable String postId, @AuthenticationPrincipal AppUser principal) {
    AppUser user = usersService.getUserById(principal.getId());

    return postsService.downvotePost(postId, user);
  }

  @DeleteMapping("/remove-vote")
  @ResponseStatus(HttpStatus.OK)
  public PostDTO removeVoteFromPost(@PathVariable String postId, @AuthenticationPrincipal AppUser principal) {
    AppUser user = usersService.getUserById(principal.getId());

    return postsService.removeVoteFromPost(postId, user);
  }

}
