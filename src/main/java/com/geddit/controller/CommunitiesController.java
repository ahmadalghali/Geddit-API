package com.geddit.controller;

import com.geddit.dto.community.CommunitySummaryDTO;
import com.geddit.dto.community.CreateCommunityDTO;
import com.geddit.persistence.entity.Community;
import com.geddit.service.CommunitiesService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/communities")
@Slf4j
public class CommunitiesController {

  private final CommunitiesService communitiesService;

  public CommunitiesController(CommunitiesService communitiesService) {
    this.communitiesService = communitiesService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CommunitySummaryDTO createCommunity(@Valid @RequestBody CreateCommunityDTO createCommunityDTO, @RequestHeader("username") String username) {
    return communitiesService.createCommunity(createCommunityDTO, username);
  }

  @PostMapping("/{communityName}/join")
  @ResponseStatus(HttpStatus.OK)
  public Integer joinCommunity(@PathVariable String communityName, @RequestHeader("username") String username) {
    return communitiesService.joinCommunity(communityName, username);
  }

  @DeleteMapping("/{communityName}/leave")
  @ResponseStatus(HttpStatus.OK)
  public Integer leaveCommunity(@PathVariable String communityName, @RequestHeader("username") String username) {
    return communitiesService.leaveCommunity(communityName, username);
  }

  @GetMapping("/{communityName}")
  @ResponseStatus(HttpStatus.OK)
  public CommunitySummaryDTO getCommunitySummaryByName(@PathVariable String communityName) {
    return communitiesService.getCommunitySummaryByName(communityName);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<CommunitySummaryDTO> getAllCommunities() {
    return communitiesService.getCommunities();
  }

}
