package com.geddit.service;

import com.geddit.converter.PostToDTOConverter;
import com.geddit.dto.post.CreatePostDTO;
import com.geddit.dto.post.PostDTO;
import com.geddit.dto.post.PostSummaryDTO;
import com.geddit.dto.post.UpdatePostDTO;
import com.geddit.exceptions.GedditException;
import com.geddit.exceptions.PostNotFoundException;
import com.geddit.persistence.entity.AppUser;
import com.geddit.persistence.entity.Community;
import com.geddit.persistence.entity.Post;
import com.geddit.persistence.repository.CommunityRepository;
import com.geddit.persistence.repository.PostRepository;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PostsService {

  private final PostRepository postRepository;
  private final CommunityRepository communityRepository;
  private final CommunitiesService communitiesService;
  private final UsersService usersService;
  private final AuthService authService;
  private final PostToDTOConverter postToDTOConverter;

  public PostsService(
          PostRepository postRepository,
          CommunityRepository communityRepository,
          CommunitiesService communitiesService,
          UsersService usersService, AuthService authService, PostToDTOConverter postToDTOConverter) {
    this.postRepository = postRepository;
    this.communityRepository = communityRepository;
    this.communitiesService = communitiesService;
    this.usersService = usersService;
    this.authService = authService;
    this.postToDTOConverter = postToDTOConverter;
  }

  public PostDTO createPost(String communityName, CreatePostDTO createPostDTO, AppUser user) {
    Community community = communitiesService.getCommunityByName(communityName);
    Post post = new Post(createPostDTO.title(), community, user);

    if (!createPostDTO.body().trim().isEmpty()) {
      post.setBody(createPostDTO.body());
    }

    return PostToDTOConverter.toDTO(postRepository.save(post), Optional.of(user));
  }

  public List<PostSummaryDTO> getAllPostsByCommunityName(String communityName) {

    return this.postToDTOConverter.toSummaryDTOList(postRepository.findAllPostsByCommunityName(communityName));
  }

  public PostDTO getPostDTOById(String postId) {

    Optional<AppUser> currentUserOptional = authService.getCurrentUser();
    return PostToDTOConverter.toDTO(getPostById(postId), currentUserOptional);
  }

  public Post getPostById(String postId) {
    return postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));
  }

  public List<PostSummaryDTO> searchPostsByKeyword(String keyword) {
    return  this.postToDTOConverter.toSummaryDTOList(postRepository.findAllByTitleContainingIgnoreCase(keyword));
  }

  public List<PostSummaryDTO> getUserPosts(String username) {
    return  this.postToDTOConverter.toSummaryDTOList(postRepository.findAllByUserEmail(username));
  }

  public void deletePost(String postId, AppUser user) {
    Post post = getPostById(postId);

    if (!post.getAuthor().getId().equals(user.getId())) {
      throw new GedditException("You are unauthorized to delete this post as you are not the author.");
    }
    postRepository.deleteById(postId);
  }

  public PostDTO updatePost(String postId, UpdatePostDTO updatePostDTO, AppUser user) {
    Post post = getPostById(postId);

    if (!post.getAuthor().getId().equals(user.getId())) {
      throw new GedditException("You are unauthorized to update this post as you are not the author.");
    }

    if (updatePostDTO.body() != null) {
      post.setBody(updatePostDTO.body());
    }

    return PostToDTOConverter.toDTO(postRepository.save(post), Optional.of(user));
  }

  public PostDTO upvotePost(String postId, AppUser user) {
    Post post = getPostById(postId);

    if (post.getDownvotedBy().contains(user)) {
      post.getDownvotedBy().remove(user);
    }

    post.getUpvotedBy().add(user);

//    if (post.getUpvotedBy().contains(user)) {
//      throw new IllegalArgumentException("User already ");
//    }

    return PostToDTOConverter.toDTO(postRepository.save(post), Optional.of(user));
  }

  public PostDTO downvotePost(String postId, AppUser user) {
    Post post = getPostById(postId);

    if (post.getUpvotedBy().contains(user)) {
      post.getUpvotedBy().remove(user);
    }

    post.getDownvotedBy().add(user);

//    if (post.getUpvotedBy().contains(user)) {
//      throw new IllegalArgumentException("User already ");
//    }

    return PostToDTOConverter.toDTO(postRepository.save(post), Optional.of(user));
  }

  public PostDTO removeVoteFromPost(String postId, AppUser user) {

    Post post = getPostById(postId);

    if (post.getUpvotedBy().contains(user)) {
      post.getUpvotedBy().remove(user);
    }

    if (post.getDownvotedBy().contains(user)) {
      post.getDownvotedBy().remove(user);
    }

    return PostToDTOConverter.toDTO(postRepository.save(post), Optional.of(user));
  }

}
