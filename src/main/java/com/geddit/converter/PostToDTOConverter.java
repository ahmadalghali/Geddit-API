package com.geddit.converter;

import com.geddit.dto.comment.CommentDTO;
import com.geddit.dto.post.PostDTO;
import com.geddit.dto.post.PostSummaryDTO;
import com.geddit.enums.ContentVoteStatus;
import com.geddit.persistence.entity.AppUser;
import com.geddit.persistence.entity.Post;

import java.util.List;
import java.util.stream.Collectors;

public class PostToDTOConverter {

    public static PostDTO toDTO(Post post) {

        List<CommentDTO> postComments =
                post.getComments().stream().map(CommentToDTOConverter::toDTO).collect(Collectors.toList());
        var voteCount = post.getUpvotedBy().size() - post.getDownvotedBy().size();
        var author = UserToDTOConverter.toDTO(post.getAuthor());

        ContentVoteStatus myVote = ContentVoteStatus.UNVOTED;

//        if (post.getDownvotedBy().contains(me)) {
//            myVote = ContentVoteStatus.DOWNVOTED;
//        } else if (post.getUpvotedBy().contains(me)) {
//            myVote = ContentVoteStatus.UPVOTED;
//        } else {
//            myVote = ContentVoteStatus.UNVOTED;
//        }

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

    public static PostSummaryDTO toSummaryDTO(Post post) {
        var voteCount = post.getUpvotedBy().size() - post.getDownvotedBy().size();
        var author = UserToDTOConverter.toDTO(post.getAuthor());

        ContentVoteStatus myVote = ContentVoteStatus.UNVOTED;

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

    public static List<PostSummaryDTO> toSummaryDTOList(List<Post> posts) {
        return posts.stream().map(PostToDTOConverter::toSummaryDTO).toList();
    }
}
