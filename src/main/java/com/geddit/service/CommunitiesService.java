package com.geddit.service;

import com.geddit.converter.CommunityToDTOConverter;
import com.geddit.dto.community.CommunitySummaryDTO;
import com.geddit.dto.community.CreateCommunityDTO;
import com.geddit.exceptions.CommunityNotFoundException;
import com.geddit.exceptions.GedditException;
import com.geddit.persistence.entity.AppUser;
import com.geddit.persistence.entity.Community;
import com.geddit.persistence.repository.CommunityRepository;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommunitiesService {

  private final UsersService usersService;
  private final AuthService authService;
  private final CommunityRepository communityRepository;

  public CommunitySummaryDTO createCommunity(CreateCommunityDTO createCommunityDTO, AppUser user) {
    boolean communityExists = communityExistsByName(createCommunityDTO.name());
    if (communityExists) throw new GedditException("Cannot create community, it already exists.");


    Community community =
        new Community(createCommunityDTO.name(), createCommunityDTO.description(), user);
    Optional<AppUser> userOptional = this.authService.getCurrentUser();

    return CommunityToDTOConverter.toDTO(saveCommunity(community), userOptional);
  }

  boolean communityExistsByName(String communityName) {
    return communityRepository.existsByNameIgnoreCase(communityName);
  }

  public List<CommunitySummaryDTO> getCommunities() {
    Optional<AppUser> userOptional = this.authService.getCurrentUser();
    return CommunityToDTOConverter.toDTOList(communityRepository.findAll(), userOptional);
  }

  public List<CommunitySummaryDTO> searchCommunitiesByKeyword(String name) {
    List<Community> results = communityRepository.findAllByNameContainingIgnoreCase(name);
    Optional<AppUser> userOptional = this.authService.getCurrentUser();

    return CommunityToDTOConverter.toDTOList(results, userOptional);
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
    Optional<AppUser> userOptional = this.authService.getCurrentUser();

    return CommunityToDTOConverter.toDTO(community, userOptional);
  }

  private Community saveCommunity(Community newCommunity) {
    return communityRepository.save(newCommunity);
  }

  public Integer joinCommunity(String communityName, AppUser user) {
    Community community = getCommunityByName(communityName);

//    TODO: future improvements, check if blocked, allowed to join etc

    if (community.getMembers().contains(user)) {
      throw new GedditException("You're already part of g/" + communityName);
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
      throw new GedditException("You're not a member of g/" + communityName);
    }
    communityRepository.save(community);

    return community.getMembers().size();
  }
}
