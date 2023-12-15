package com.geddit.controller;

import com.geddit.dto.post.PostDTO;
import com.geddit.dto.post.UpdatePostDTO;
import com.geddit.persistence.entity.AppUser;
import com.geddit.service.PostsService;
import com.geddit.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostsController {

  private final PostsService postsService;
  private final UsersService usersService;

  public PostsController(PostsService postsService, UsersService usersService) {
    this.postsService = postsService;
    this.usersService = usersService;
  }

  @GetMapping("/{postId}")
  @ResponseStatus(HttpStatus.OK)
  public PostDTO getPostById(@PathVariable String postId) {
    return postsService.getPostDTOById(postId);
  }

  @DeleteMapping("/{postId}")
  @ResponseStatus(HttpStatus.OK)
  public void deletePost(@PathVariable String postId, @AuthenticationPrincipal AppUser principal) {
    AppUser user = usersService.getUserById(principal.getId());

    postsService.deletePost(postId, user);
  }

  @PatchMapping("/{postId}")
  @ResponseStatus(HttpStatus.OK)
  public PostDTO updatePost(@PathVariable String postId, @RequestBody UpdatePostDTO updatePostDTO, @AuthenticationPrincipal AppUser principal) {
    AppUser user = usersService.getUserById(principal.getId());

    return postsService.updatePost(postId, updatePostDTO, user);
  }

}
