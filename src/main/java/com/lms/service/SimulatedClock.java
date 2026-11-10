package com.lms.service;

public class SimulatedClock{
    private int today;
    public int today(){
        return today;
    }
    public int advance(int days){
        if(days<0){
            throw new IllegalArgumentException("days must not be negative: " + days);
        }
        today+=days;
        return today;
    }
}