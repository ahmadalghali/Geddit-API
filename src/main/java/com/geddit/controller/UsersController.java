package com.geddit.controller;

import com.geddit.converter.UserToDTOConverter;
import com.geddit.dto.UserDTO;
import com.geddit.dto.comment.CommentDTO;
import com.geddit.dto.post.PostSummaryDTO;
import com.geddit.service.CommentsService;
import com.geddit.service.PostsService;
import com.geddit.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;
    private final PostsService postsService;
    private final CommentsService commentsService;

    public UsersController(UsersService usersService, PostsService postsService, CommentsService commentsService) {
        this.usersService = usersService;
        this.postsService = postsService;
        this.commentsService = commentsService;
    }

    @GetMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserProfile(@PathVariable String username) {
        return UserToDTOConverter.toDTO(usersService.getUserByUsername(username));
    }

    @GetMapping("/{username}/posts")
    @ResponseStatus(HttpStatus.OK)
    public List<PostSummaryDTO> getUserPosts(@PathVariable String username) {
        return postsService.getUserPosts(username);
    }

    @GetMapping("/{username}/comments")
    @ResponseStatus(HttpStatus.OK)
    public Set<CommentDTO> getUserComments(@PathVariable String username) {
        return commentsService.getUserComments(username);
    }

    @PostMapping("/{username}/follow")
    @ResponseStatus(HttpStatus.CREATED)
    public void followUser(@PathVariable("username") String usernameToFollow, @RequestHeader("username") String username) {
        usersService.followUser(usernameToFollow, username);
    }

    @DeleteMapping("/{username}/unfollow")
    @ResponseStatus(HttpStatus.OK)
    public void unfollowUser(@PathVariable("username") String usernameToUnfollow, @RequestHeader("username") String username) {
        usersService.unfollowUser(usernameToUnfollow, username);
    }

}
