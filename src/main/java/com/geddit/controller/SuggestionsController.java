package com.geddit.controller;

import com.geddit.dto.post.PostSummaryDTO;
import com.geddit.service.SuggestionsService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/suggestions")
public class SuggestionsController {

    private final SuggestionsService suggestionsService;

    public SuggestionsController(SuggestionsService suggestionsService) {
        this.suggestionsService = suggestionsService;
    }

    @GetMapping("/posts")
    public List<PostSummaryDTO> getSuggestedPosts() {
        return suggestionsService.getSuggestedPosts();
    }

}
