package com.geddit.service;

import com.geddit.converter.PostToDTOConverter;
import com.geddit.dto.post.PostSummaryDTO;
import com.geddit.persistence.repository.PostRepository;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SuggestionsService {

  private final PostRepository postRepository;
  private final PostToDTOConverter postToDTOConverter;


  public List<PostSummaryDTO> getSuggestedPosts() {
    return  this.postToDTOConverter.toSummaryDTOList(postRepository.findMostRecentPostsLimit20());
  }
}
