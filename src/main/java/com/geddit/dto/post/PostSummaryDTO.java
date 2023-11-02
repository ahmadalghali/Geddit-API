package com.geddit.dto.post;

import com.geddit.dto.UserDTO;
import com.geddit.enums.ContentVoteStatus;

import java.time.LocalDateTime;

public record PostSummaryDTO(
    String id,
    String title,
    String body,
    String communityName,
    LocalDateTime createdDate,
    Integer commentCount,
    UserDTO author,
    Integer voteCount,
    ContentVoteStatus myVote
//    Integer upvotes,
//    Integer downvotes
    ) {}
