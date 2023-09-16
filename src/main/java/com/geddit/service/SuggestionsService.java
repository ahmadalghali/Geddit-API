package com.geddit.service;

import com.geddit.dto.post.PostSummaryDTO;
import com.geddit.persistence.repository.PostRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SuggestionsService {

  private final PostRepository postRepository;

  public SuggestionsService(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  public List<PostSummaryDTO> getSuggestedPosts() {
    return postRepository.findMostRecentPostsLimit20();
  }
}
