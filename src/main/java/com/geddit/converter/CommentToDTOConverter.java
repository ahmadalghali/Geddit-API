package com.geddit.converter;

import com.geddit.dto.comment.CommentDTO;
import com.geddit.enums.ContentVoteStatus;
import com.geddit.persistence.entity.Comment;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class CommentToDTOConverter {
  public static CommentDTO toDTO(Comment comment) {

    String parentCommentId = null;

    if (comment.getParentComment().isPresent()) {
      parentCommentId = comment.getParentComment().get().getId();
    }

    Set<CommentDTO> replies = toDTOSet(comment.getReplies());
    var author = UserToDTOConverter.toDTO(comment.getAuthor());
    var voteCount = comment.getUpvotedBy().size() - comment.getDownvotedBy().size();
    ContentVoteStatus myVote = ContentVoteStatus.UNVOTED;

    String communityName = comment.getPost().getCommunity().getName();
    String postId = comment.getPost().getId();
    String commentId = comment.getId();
    String clientBaseUrl = "http://localhost:5173";

    String href = String.format("%s/g/%s/posts/%s?highlightedCommentId=%s", clientBaseUrl, communityName, postId, commentId);

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

  public static Set<CommentDTO> toDTOSet(List<Comment> comments) {
    return comments.stream().map(CommentToDTOConverter::toDTO).collect(Collectors.toSet());
  }

  public static Set<CommentDTO> toDTOSet(Set<Comment> comments) {
    return comments.stream().map(CommentToDTOConverter::toDTO).collect(Collectors.toSet());
  }
}
