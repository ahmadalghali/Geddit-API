package com.geddit.controller;

import com.geddit.dto.post.CreatePostDTO;
import com.geddit.dto.post.PostDTO;
import com.geddit.dto.post.PostSummaryDTO;
import com.geddit.persistence.entity.AppUser;
import com.geddit.service.PostsService;
import java.util.List;

import com.geddit.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/communities/{communityName}/posts")
public class CommunityPostsController {

  private final PostsService postsService;
  private final UsersService usersService;

  public CommunityPostsController(PostsService postsService, UsersService usersService) {
    this.postsService = postsService;
    this.usersService = usersService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public PostDTO createPost(
      @PathVariable String communityName,
      @Valid @RequestBody CreatePostDTO createPostDTO,
      @AuthenticationPrincipal AppUser principal
  ) {

    AppUser user = usersService.getUserById(principal.getId());

    return postsService.createPost(communityName, createPostDTO, user);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<PostSummaryDTO> getAllPostsByCommunityName(@PathVariable String communityName) {
    return postsService.getAllPostsByCommunityName(communityName);
  }

  @GetMapping("/{postId}")
  @ResponseStatus(HttpStatus.OK)
  public PostDTO getPost(@PathVariable String postId) {
    return postsService.getPostDTOById(postId);
  }
}
