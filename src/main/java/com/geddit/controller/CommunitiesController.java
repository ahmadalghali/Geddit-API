package com.geddit.controller;

import com.geddit.dto.community.CommunitySummaryDTO;
import com.geddit.dto.community.CreateCommunityDTO;
import com.geddit.persistence.entity.AppUser;
import com.geddit.service.CommunitiesService;
import com.geddit.service.UsersService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/communities")
@Slf4j
public class CommunitiesController {

  private final CommunitiesService communitiesService;
  private final UsersService usersService;

  public CommunitiesController(CommunitiesService communitiesService, UsersService usersService) {
    this.communitiesService = communitiesService;
    this.usersService = usersService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CommunitySummaryDTO createCommunity(@Valid @RequestBody CreateCommunityDTO createCommunityDTO, @AuthenticationPrincipal AppUser principal) {
    AppUser user = usersService.getUserById(principal.getId());

    return communitiesService.createCommunity(createCommunityDTO, user);
  }

  @PostMapping("/{communityName}/join")
  @ResponseStatus(HttpStatus.OK)
  public Integer joinCommunity(@PathVariable String communityName, @AuthenticationPrincipal AppUser principal) {
    AppUser user = usersService.getUserById(principal.getId());

    return communitiesService.joinCommunity(communityName, user);
  }

  @DeleteMapping("/{communityName}/leave")
  @ResponseStatus(HttpStatus.OK)
  public Integer leaveCommunity(@PathVariable String communityName, @AuthenticationPrincipal AppUser principal) {
    AppUser user = usersService.getUserById(principal.getId());

    return communitiesService.leaveCommunity(communityName, user);
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
