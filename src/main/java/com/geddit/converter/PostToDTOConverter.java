package com.geddit.converter;

import com.geddit.dto.comment.CommentDTO;
import com.geddit.dto.post.PostDTO;
import com.geddit.dto.post.PostSummaryDTO;
import com.geddit.enums.ContentVoteStatus;
import com.geddit.persistence.entity.AppUser;
import com.geddit.persistence.entity.Post;
import com.geddit.service.AuthService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PostToDTOConverter {
    private final AuthService authService;

    public PostToDTOConverter(AuthService authService) {
        this.authService = authService;
    }

    public static PostDTO toDTO(Post post, Optional<AppUser> userOptional) {

        List<CommentDTO> postComments =
                post.getComments().stream().map(comment -> CommentToDTOConverter.toDTO(comment, userOptional)).collect(Collectors.toList());
        var voteCount = post.getUpvotedBy().size() - post.getDownvotedBy().size();
        var author = UserToDTOConverter.toDTO(post.getAuthor());

        ContentVoteStatus myVote;

        if (userOptional.isPresent()) {
            AppUser user = userOptional.get();
            if (post.getDownvotedBy().contains(user)) {
                myVote = ContentVoteStatus.DOWNVOTED;
            } else if (post.getUpvotedBy().contains(user)) {
                myVote = ContentVoteStatus.UPVOTED;
            } else {
                myVote = ContentVoteStatus.UNVOTED;
            }
        } else {
            myVote = ContentVoteStatus.UNVOTED;
        }

        return new PostDTO(
                post.getId(),
                post.getTitle(),
                post.getBody(),
                post.getCommunity().getName(),
                post.getCreatedDate(),
                postComments,
                author,
                voteCount,
                myVote
//                post.getUpvotedBy().size(),
//                post.getDownvotedBy().size()
        );
    }

    public  PostSummaryDTO toSummaryDTO(Post post) {
        var voteCount = post.getUpvotedBy().size() - post.getDownvotedBy().size();
        var author = UserToDTOConverter.toDTO(post.getAuthor());

        ContentVoteStatus myVote;

        Optional<AppUser> userOptional = this.authService.getCurrentUser();
        if (userOptional.isPresent()) {
            AppUser user = userOptional.get();
            if (post.getDownvotedBy().contains(user)) {
                myVote = ContentVoteStatus.DOWNVOTED;
            } else if (post.getUpvotedBy().contains(user)) {
                myVote = ContentVoteStatus.UPVOTED;
            } else {
                myVote = ContentVoteStatus.UNVOTED;
            }
        } else {
            myVote = ContentVoteStatus.UNVOTED;
        }


//        if (post.getDownvotedBy().contains(me)) {
//            myVote = ContentVoteStatus.DOWNVOTED;
//        } else if (post.getUpvotedBy().contains(me)) {
//            myVote = ContentVoteStatus.UPVOTED;
//        } else {
//            myVote = ContentVoteStatus.UNVOTED;
//        }


        return new PostSummaryDTO(
                post.getId(),
                post.getTitle(),
                post.getBody(),
                post.getCommunity().getName(),
                post.getCreatedDate(),
                post.getComments().size(),
                author,
                voteCount,
                myVote
        );
    }

    public List<PostSummaryDTO> toSummaryDTOList(List<Post> posts) {
        return posts.stream().map(this::toSummaryDTO).toList();
    }
}
