package com.geddit.exceptions;

public class CommunityNotFoundException extends RuntimeException {

    public CommunityNotFoundException(String communityName) {
        super("Community not found with name: " + communityName);
    }
}

