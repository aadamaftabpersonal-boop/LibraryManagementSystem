package com.lms.domain.member;
import java.util.Objects;

public final class Staff extends Member{
    private static final int MAX_ITEMS = 4;
    private static final int LOAN_PERIOD_DAYS = 21;

    public Staff(String id, String name){
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