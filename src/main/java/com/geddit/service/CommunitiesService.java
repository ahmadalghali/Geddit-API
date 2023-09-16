package com.geddit.service;

import com.geddit.converter.CommunityToDTOConverter;
import com.geddit.dto.community.CommunitySummaryDTO;
import com.geddit.dto.community.CreateCommunityDTO;
import com.geddit.exceptions.CommunityNotFoundException;
import com.geddit.persistence.entity.Community;
import com.geddit.persistence.repository.CommunityRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CommunitiesService {

  private final CommunityRepository communityRepository;

  public CommunitiesService(CommunityRepository communityRepository) {
    this.communityRepository = communityRepository;
  }

  public CommunitySummaryDTO createCommunity(CreateCommunityDTO createCommunityDTO) {
    boolean communityExists = communityRepository.existsByName(createCommunityDTO.getName());
    if (communityExists) throw new IllegalArgumentException("Community exists");

    Community community =
        new Community(createCommunityDTO.getName(), createCommunityDTO.getDescription());
    return CommunityToDTOConverter.toDTO(saveCommunity(community));
  }

  public List<CommunitySummaryDTO> getCommunities() {
    return communityRepository.findAllCommunitiesWithPostCounts();
  }

  public List<CommunitySummaryDTO> searchCommunitiesByKeyword(String name) {
    return communityRepository.findAllByNameContainingIgnoreCase(name);
  }

  public Community getCommunityByName(String communityName) {
    return communityRepository
        .findCommunityByName(communityName)
        .orElseThrow(() -> new CommunityNotFoundException(communityName));
  }

  public CommunitySummaryDTO getCommunitySummaryByName(String communityName) {
    return communityRepository
        .findCommunitySummaryByName(communityName)
        .orElseThrow(() -> new CommunityNotFoundException(communityName));
  }

  private Community saveCommunity(Community newCommunity) {
    return communityRepository.save(newCommunity);
  }
}
