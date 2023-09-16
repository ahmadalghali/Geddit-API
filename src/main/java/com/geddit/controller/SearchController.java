package com.geddit.controller;

import com.geddit.dto.UserDTO;
import com.geddit.dto.community.CommunitySummaryDTO;
import com.geddit.dto.post.PostSummaryDTO;
import com.geddit.service.SearchService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/communities")
    @ResponseStatus(HttpStatus.OK)
    public List<CommunitySummaryDTO> searchCommunitiesByKeyword(@RequestParam String keyword) {
        return searchService.searchCommunitiesByKeyword(keyword);
    }

    @GetMapping("/posts")
    @ResponseStatus(HttpStatus.OK)
    public List<PostSummaryDTO> searchPostsByKeyword(@RequestParam String keyword) {
        return searchService.searchPostsByKeyword(keyword);
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> searchUsersByKeyword(@RequestParam String keyword) {
        return searchService.searchUsersByKeyword(keyword);
    }
}

