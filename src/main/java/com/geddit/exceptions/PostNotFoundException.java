package com.geddit.exceptions;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException(String postId) {
        super("Post not found with id: " + postId);
    }
}

