package com.geddit.service;

import com.geddit.dto.UserDTO;
import com.geddit.dto.community.CommunitySummaryDTO;
import com.geddit.dto.post.PostSummaryDTO;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
    private final CommunityService communityService;
    private final PostService postService;
    private final UserService userService;

    public SearchService(CommunityService communityService, PostService postService, UserService userService) {
        this.communityService = communityService;
        this.postService = postService;
        this.userService = userService;
    }

    public List<CommunitySummaryDTO> searchCommunitiesByKeyword(String keyword) {
        return communityService.searchCommunitiesByKeyword(keyword);
    }

    public List<PostSummaryDTO> searchPostsByKeyword(String keyword) {
        return postService.searchPostsByKeyword(keyword);
    }

    public List<UserDTO> searchUsersByKeyword(String keyword) {
        return userService.searchUsersByKeyword(keyword);
    }
}
