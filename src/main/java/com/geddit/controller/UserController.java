package com.geddit.controller;

import com.geddit.converter.UserToDTOConverter;
import com.geddit.dto.UserDTO;
import com.geddit.dto.post.PostSummaryDTO;
import com.geddit.persistence.entity.Comment;
import com.geddit.service.CommentService;
import com.geddit.service.PostService;
import com.geddit.service.UserService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;

    public UserController(UserService userService, PostService postService, CommentService commentService) {
        this.userService = userService;
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserProfile(@PathVariable String username) {
        return UserToDTOConverter.toDTO(userService.getUserByUsername(username));
    }

    @GetMapping("/{username}/posts")
    @ResponseStatus(HttpStatus.OK)
    public List<PostSummaryDTO> getUserPosts(@PathVariable String username) {
        return postService.getUserPosts(username);
    }

    @GetMapping("/{username}/comments")
    @ResponseStatus(HttpStatus.OK)
    public List<Comment> getUserComments(@PathVariable String username) {
        return commentService.getUserComments(username);
    }



}