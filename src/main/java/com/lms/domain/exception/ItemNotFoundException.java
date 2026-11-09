package com.lms.domain.exception;

public class ItemNotFoundException extends LibraryException{
    public ItemNotFoundException(String itemId){
        super("item " + itemId + " not found");
    }
}