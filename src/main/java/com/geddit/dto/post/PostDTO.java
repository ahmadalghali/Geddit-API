package com.geddit.dto.post;

import com.geddit.dto.UserDTO;
import com.geddit.dto.comment.CommentDTO;
import com.geddit.enums.ContentVoteStatus;

import java.time.LocalDateTime;
import java.util.List;

public record PostDTO(
    String id,
    String title,
    String body,
    String communityName,
    LocalDateTime createdDate,
    List<CommentDTO> comments,
    UserDTO author,
    Integer voteCount,
    ContentVoteStatus userVoteStatus
//    Integer upvotes,
//    Integer downvotes
    ) {}
