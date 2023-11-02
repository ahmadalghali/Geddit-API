package com.geddit.service;

import com.geddit.converter.PostToDTOConverter;
import com.geddit.dto.post.CreatePostDTO;
import com.geddit.dto.post.PostDTO;
import com.geddit.dto.post.PostSummaryDTO;
import com.geddit.dto.post.UpdatePostDTO;
import com.geddit.exceptions.PostNotFoundException;
import com.geddit.persistence.entity.AppUser;
import com.geddit.persistence.entity.Community;
import com.geddit.persistence.entity.Post;
import com.geddit.persistence.repository.CommunityRepository;
import com.geddit.persistence.repository.PostRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PostsService {

  private final PostRepository postRepository;
  private final CommunityRepository communityRepository;
  private final CommunitiesService communitiesService;
  private final UsersService usersService;

  public PostsService(
      PostRepository postRepository,
      CommunityRepository communityRepository,
      CommunitiesService communitiesService,
      UsersService usersService) {
    this.postRepository = postRepository;
    this.communityRepository = communityRepository;
    this.communitiesService = communitiesService;
    this.usersService = usersService;
  }

  public PostDTO createPost(String communityName, CreatePostDTO createPostDTO, String username) {
    Community community = communitiesService.getCommunityByName(communityName);
    AppUser author = usersService.getUserByUsername(username);
    Post post = new Post(createPostDTO.title(), community, author);

    if (!createPostDTO.body().trim().isEmpty()) {
      post.setBody(createPostDTO.body());
    }

    return PostToDTOConverter.toDTO(postRepository.save(post));
  }

  public List<PostSummaryDTO> getAllPostsByCommunityName(String communityName) {

    return PostToDTOConverter.toSummaryDTOList(postRepository.findAllPostsByCommunityName(communityName));
  }

  public PostDTO getPostDTOById(String postId) {
    return PostToDTOConverter.toDTO(getPostById(postId));
  }

  public Post getPostById(String postId) {
    return postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));
  }

  public List<PostSummaryDTO> searchPostsByKeyword(String keyword) {
    return PostToDTOConverter.toSummaryDTOList(postRepository.findAllByTitleContainingIgnoreCase(keyword));
  }

  public List<PostSummaryDTO> getUserPosts(String username) {
    return PostToDTOConverter.toSummaryDTOList(postRepository.findAllByUsername(username));
  }

  public void deletePost(String postId, String username) {
    postRepository.deleteById(postId);
  }

  public PostDTO updatePost(String postId, UpdatePostDTO updatePostDTO, String username) {
    Post post = getPostById(postId);
    AppUser me = usersService.getUserByUsername(username);

    if (updatePostDTO.body() != null) {
      post.setBody(updatePostDTO.body());
    }

    return PostToDTOConverter.toDTO(postRepository.save(post));
  }

  public PostDTO upvotePost(String postId, String username) {
    Post post = getPostById(postId);
    AppUser user = usersService.getUserByUsername(username);

    if (post.getDownvotedBy().contains(user)) {
      post.getDownvotedBy().remove(user);
    }

    post.getUpvotedBy().add(user);

//    if (post.getUpvotedBy().contains(user)) {
//      throw new IllegalArgumentException("User already ");
//    }

    return PostToDTOConverter.toDTO(postRepository.save(post));
  }

  public PostDTO downvotePost(String postId, String username) {
    Post post = getPostById(postId);
    AppUser user = usersService.getUserByUsername(username);

    if (post.getUpvotedBy().contains(user)) {
      post.getUpvotedBy().remove(user);
    }

    post.getDownvotedBy().add(user);

//    if (post.getUpvotedBy().contains(user)) {
//      throw new IllegalArgumentException("User already ");
//    }

    return PostToDTOConverter.toDTO(postRepository.save(post));
  }

  public PostDTO removeVoteFromPost(String postId, String username) {

    Post post = getPostById(postId);
    AppUser user = usersService.getUserByUsername(username);

    if (post.getUpvotedBy().contains(user)) {
      post.getUpvotedBy().remove(user);
    }

    if (post.getDownvotedBy().contains(user)) {
      post.getDownvotedBy().remove(user);
    }

    return PostToDTOConverter.toDTO(postRepository.save(post));
  }
}
