package com.geddit.dto.post;

import com.geddit.dto.UserDTO;
import com.geddit.enums.ContentVoteStatus;

import java.time.Instant;

public record PostSummaryDTO(
    String id,
    String title,
    String body,
    String communityName,
    Instant createdDate,
    Integer commentCount,
    UserDTO author,
    Integer voteCount,
    ContentVoteStatus userVoteStatus
//    Integer upvotes,
//    Integer downvotes
    ) {}
