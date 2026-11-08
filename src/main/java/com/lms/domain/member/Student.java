package com.lms.domain.member;
import java.util.Objects;

public final class Student extends Member{
    private static final int MAX_ITEMS = 3;
    private static final int LOAN_PERIOD_DAYS = 14;

    public Student(String id, String name){
        super(id, name);
    }

    @Override
    public int getMaxItems(){
        return MAX_ITEMS;
    }
    @Override
    public int getLoanPeriodDays(){
        return LOAN_PERIOD_DAYS;
    }
}