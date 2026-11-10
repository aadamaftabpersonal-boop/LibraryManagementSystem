package com.lms.domain.exception;

public class BorrowLimitExceededException extends LibraryException{
    public BorrowLimitExceededException(String memberid, int max){
        super(memberid + " has reached the limit of " + max);
    }
}