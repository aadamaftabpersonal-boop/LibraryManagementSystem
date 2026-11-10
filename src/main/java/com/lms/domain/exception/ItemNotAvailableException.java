package com.lms.domain.exception;

public class ItemNotAvailableException extends LibraryException{
    private ItemNotAvailableException(String message){
        super(message);
    }
    public static ItemNotAvailableException onLoan(String itemid){
        return new ItemNotAvailableException(itemid + " is on loan");
    }
    public static ItemNotAvailableException notBorrowable(String itemid){
        return new ItemNotAvailableException(itemid + " cannot be borrowed");
    }
}