package com.geddit.dto.post;

import com.geddit.dto.comment.CommentDTO;
import java.time.LocalDateTime;
import java.util.List;

public record PostDTO(
    String id,
    String title,
    String body,
    String communityName,
    LocalDateTime createdDate,
    List<CommentDTO> comments,
    String authorUsername
    ) {}
