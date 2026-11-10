package com.lms.domain.exception;

public class ItemNotOnLoanException extends LibraryException{
    public ItemNotOnLoanException(String itemid){
        super(itemid + " is not on loan");
    }
}