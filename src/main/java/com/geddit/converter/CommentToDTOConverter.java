package com.geddit.converter;

import com.geddit.dto.comment.CommentDTO;
import com.geddit.persistence.entity.Comment;
import java.util.Set;
import java.util.stream.Collectors;

public class CommentToDTOConverter {
  public static CommentDTO toDTO(Comment comment) {

    Set<CommentDTO> replies = toDTOSet(comment.getReplies());
    return new CommentDTO(
        comment.getId(),
        comment.getText(),
        comment.getAuthor().getUsername(),
        comment.getPost().getId(),
        replies,
        comment.getCreatedDate());
  }

  public static Set<CommentDTO> toDTOSet(Set<Comment> comments) {
    return comments.stream().map(CommentToDTOConverter::toDTO).collect(Collectors.toSet());
  }
}
