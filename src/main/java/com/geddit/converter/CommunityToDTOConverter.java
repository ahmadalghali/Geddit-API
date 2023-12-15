package com.geddit.converter;

import com.geddit.dto.UserDTO;
import com.geddit.dto.community.CommunitySummaryDTO;
import com.geddit.dto.post.PostDTO;
import com.geddit.persistence.entity.Community;

import java.util.List;
import java.util.Optional;

public class CommunityToDTOConverter {
    public static CommunitySummaryDTO toDTO(Community community) {
        List<PostDTO> postDTOList =
                community.getPosts().stream().map(post -> PostToDTOConverter.toDTO(post, Optional.empty())).toList();
        UserDTO createdBy = UserToDTOConverter.toDTO(community.getCreatedBy());
        return new CommunitySummaryDTO(
                community.getName(),
                community.getDescription(),
                community.getImageUrl(),
                postDTOList.stream().count(),
                community.getMembers().size(),
                community.getCreatedDate(),
                createdBy);
    }

    public static List<CommunitySummaryDTO> toDTOList(List<Community> communities) {
        return communities.stream().map(CommunityToDTOConverter::toDTO).toList();
    }
}
