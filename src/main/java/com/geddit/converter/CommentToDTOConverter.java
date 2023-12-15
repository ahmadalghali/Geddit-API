package com.geddit.converter;

import com.geddit.dto.comment.CommentDTO;
import com.geddit.enums.ContentVoteStatus;
import com.geddit.persistence.entity.AppUser;
import com.geddit.persistence.entity.Comment;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class CommentToDTOConverter {
  public static CommentDTO toDTO(Comment comment, Optional<AppUser> userOptional) {

    String parentCommentId = null;

    if (comment.getParentComment().isPresent()) {
      parentCommentId = comment.getParentComment().get().getId();
    }

    Set<CommentDTO> replies = toDTOSet(comment.getReplies(), userOptional);
    var author = UserToDTOConverter.toDTO(comment.getAuthor());
    var voteCount = comment.getUpvotedBy().size() - comment.getDownvotedBy().size();

    String communityName = comment.getPost().getCommunity().getName();
    String postId = comment.getPost().getId();
    String commentId = comment.getId();
    String clientBaseUrl = "http://localhost:5173";

    String href = String.format("%s/g/%s/posts/%s?highlightedCommentId=%s", clientBaseUrl, communityName, postId, commentId);

    ContentVoteStatus myVote;

    if (userOptional.isPresent()) {
      AppUser user = userOptional.get();
      if (comment.getDownvotedBy().contains(user)) {
        myVote = ContentVoteStatus.DOWNVOTED;
      } else if (comment.getUpvotedBy().contains(user)) {
        myVote = ContentVoteStatus.UPVOTED;
      } else {
        myVote = ContentVoteStatus.UNVOTED;
      }
    } else {
      myVote = ContentVoteStatus.UNVOTED;
    }

    return new CommentDTO(
        comment.getId(),
        comment.getText(),
        author,
        comment.getPost().getId(),
        parentCommentId,
        replies,
        voteCount,
        myVote,
        href,
        comment.getCreatedDate());
  }

  public static Set<CommentDTO> toDTOSet(List<Comment> comments, Optional<AppUser> userOptional) {
    return comments.stream().map(comment -> CommentToDTOConverter.toDTO(comment, userOptional)).collect(Collectors.toSet());
  }

  public static Set<CommentDTO> toDTOSet(Set<Comment> comments, Optional<AppUser> userOptional) {
    return comments.stream().map(comment -> CommentToDTOConverter.toDTO(comment, userOptional)).collect(Collectors.toSet());
  }
}
