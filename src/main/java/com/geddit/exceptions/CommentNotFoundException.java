package com.geddit.exceptions;

public class CommentNotFoundException extends RuntimeException {

    public CommentNotFoundException(String commentId) {
        super("Comment not found with id: " + commentId);
    }
}

