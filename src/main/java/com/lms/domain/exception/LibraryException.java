package com.lms.domain.exception;
public abstract class LibraryException extends RuntimeException{
    protected LibraryException(String message){
        super(message);
    }
}