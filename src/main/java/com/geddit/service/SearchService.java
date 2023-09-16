package com.geddit.service;

import com.geddit.dto.UserDTO;
import com.geddit.dto.community.CommunitySummaryDTO;
import com.geddit.dto.post.PostSummaryDTO;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
    private final CommunitiesService communitiesService;
    private final PostsService postsService;
    private final UsersService usersService;

    public SearchService(CommunitiesService communitiesService, PostsService postsService, UsersService usersService) {
        this.communitiesService = communitiesService;
        this.postsService = postsService;
        this.usersService = usersService;
    }

    public List<CommunitySummaryDTO> searchCommunitiesByKeyword(String keyword) {
        return communitiesService.searchCommunitiesByKeyword(keyword);
    }

    public List<PostSummaryDTO> searchPostsByKeyword(String keyword) {
        return postsService.searchPostsByKeyword(keyword);
    }

    public List<UserDTO> searchUsersByKeyword(String keyword) {
        return usersService.searchUsersByKeyword(keyword);
    }
}

