package com.geddit.controller;

import com.geddit.dto.community.CommunitySummaryDTO;
import com.geddit.dto.community.CreateCommunityDTO;
import com.geddit.persistence.entity.Community;
import com.geddit.service.CommunitiesService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
  public CommunitySummaryDTO createCommunity(@RequestBody CreateCommunityDTO createCommunityDTO) {
    return communitiesService.createCommunity(createCommunityDTO);
  }

  @GetMapping("/{communityName}")
  @ResponseStatus(HttpStatus.OK)
  public CommunitySummaryDTO getCommunitySummaryByName(@PathVariable String communityName) {
    return communitiesService.getCommunitySummaryByName(communityName);
  }

//  TODO: Mark as dev only endpoint
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<CommunitySummaryDTO> getAllCommunities() {
    return communitiesService.getCommunities();
  }

}
