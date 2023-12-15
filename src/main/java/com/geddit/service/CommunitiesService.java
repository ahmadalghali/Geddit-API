package com.geddit.service;

import com.geddit.converter.CommunityToDTOConverter;
import com.geddit.dto.community.CommunitySummaryDTO;
import com.geddit.dto.community.CreateCommunityDTO;
import com.geddit.exceptions.CommunityNotFoundException;
import com.geddit.persistence.entity.AppUser;
import com.geddit.persistence.entity.Community;
import com.geddit.persistence.repository.CommunityRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CommunitiesService {

  private final UsersService usersService;
  private final CommunityRepository communityRepository;

  public CommunitiesService(UsersService usersService, CommunityRepository communityRepository) {
    this.usersService = usersService;
    this.communityRepository = communityRepository;
  }

  public CommunitySummaryDTO createCommunity(CreateCommunityDTO createCommunityDTO, AppUser user) {
    boolean communityExists = communityExistsByName(createCommunityDTO.name());
    if (communityExists) throw new IllegalArgumentException("Community exists");


    Community community =
        new Community(createCommunityDTO.name(), createCommunityDTO.description(), user);
    return CommunityToDTOConverter.toDTO(saveCommunity(community));
  }

  boolean communityExistsByName(String communityName) {
    return communityRepository.existsByName(communityName);
  }

  public List<CommunitySummaryDTO> getCommunities() {
    return CommunityToDTOConverter.toDTOList(communityRepository.findAll());
  }

  public List<CommunitySummaryDTO> searchCommunitiesByKeyword(String name) {
    List<Community> results = communityRepository.findAllByNameContainingIgnoreCase(name);
    return CommunityToDTOConverter.toDTOList(results);
  }

  public Community getCommunityByName(String communityName) {
    return communityRepository
        .findCommunityByName(communityName)
        .orElseThrow(() -> new CommunityNotFoundException(communityName));
  }

  public CommunitySummaryDTO getCommunitySummaryByName(String communityName) {
    Community community = communityRepository
            .findCommunitySummaryByName(communityName)
            .orElseThrow(() -> new CommunityNotFoundException(communityName));
    return CommunityToDTOConverter.toDTO(community);
  }

  private Community saveCommunity(Community newCommunity) {
    return communityRepository.save(newCommunity);
  }

  public Integer joinCommunity(String communityName, AppUser user) {
    Community community = getCommunityByName(communityName);

//    TODO: future improvements, check if blocked, allowed to join etc

    if (community.getMembers().contains(user)) {
      throw new IllegalArgumentException("already part of this community");
    }

    community.getMembers().add(user);
    communityRepository.save(community);

    return community.getMembers().size();
  }

  public Integer leaveCommunity(String communityName, AppUser user) {
    Community community = getCommunityByName(communityName);

//    TODO: future improvements, check if blocked, allowed to join etc

    if (community.getMembers().contains(user)) {
      community.getMembers().remove(user);
    } else {
      throw new IllegalArgumentException("user is not part of this community");
    }
    communityRepository.save(community);

    return community.getMembers().size();
  }
}
