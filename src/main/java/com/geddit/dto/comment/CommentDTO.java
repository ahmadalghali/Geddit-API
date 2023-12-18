package com.geddit.dto.comment;

import com.geddit.dto.UserDTO;
import com.geddit.enums.ContentVoteStatus;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;

public record CommentDTO(
    String id,
    String text,
    UserDTO author,
    String postId,
    String parentCommentId,
    Set<CommentDTO> replies,
    Integer voteCount,
    ContentVoteStatus userVoteStatus,
    String href,

    Instant createdDate) {}
