package com.example.AnimeBase.services;

public class IllegalNameException extends RuntimeException {
    public IllegalNameException(String message) {
        super(message);
    }
}
