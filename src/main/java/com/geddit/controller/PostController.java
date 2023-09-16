package com.geddit.controller;

import com.geddit.dto.post.CreatePostDTO;
import com.geddit.dto.post.PostDTO;
import com.geddit.dto.post.PostSummaryDTO;
import com.geddit.dto.post.UpdatePostDTO;
import com.geddit.service.PostService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public PostDTO createPost(
      @PathVariable String communityName,
      @RequestBody CreatePostDTO createPostDTO,
      @RequestHeader("username") String username) {
    System.out.println("username = " + username);
    return postService.createPost(communityName, createPostDTO, username);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<PostSummaryDTO> getAllPostsByCommunityName(@PathVariable String communityName) {
    return postService.getAllPostsByCommunityName(communityName);
  }

  @GetMapping("/{postId}")
  @ResponseStatus(HttpStatus.OK)
  public PostDTO getPost(@PathVariable String postId) {
    return postService.getPost(postId);
  }

  @DeleteMapping("/{postId}")
  @ResponseStatus(HttpStatus.OK)
  public void deletePost(@PathVariable String postId) {
    postService.deletePost(postId);
  }

  @PatchMapping("/{postId}")
  @ResponseStatus(HttpStatus.OK)
  public PostDTO updatePost(@PathVariable String postId, @RequestBody UpdatePostDTO updatePostDTO) {
    return postService.updatePost(postId, updatePostDTO);
  }
}
