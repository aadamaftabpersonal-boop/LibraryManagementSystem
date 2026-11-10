package com.lms.domain.loan;

import com.lms.domain.item.Borrowable;
import com.lms.domain.member.Member;

import java.util.Objects;

public class Loan{
    private final Borrowable item;
    private final Member member;
    private final int borrowDay;
    private final int dueDay;

    public Loan(Borrowable item, Member member, int borrowDay){
        this.item = item;
        this.member = member;
        this.borrowDay = borrowDay;
        this.dueDay = borrowDay + member.getLoanPeriodDays();
    }
    public Borrowable getItem(){
        return item;
    }
    public Member getMember(){
        return member;
    }
    public int getBorrowDay(){
        return borrowDay;
    }
    public int getDueDay(){
        return dueDay;
    }
    public int daysLate(int today){
        return Math.max(0, today - dueDay);
    }
    public int fineAsOf(int today){
        return daysLate(today) * item.getFinePerDay();
    }
    public boolean isOverdue(int today){
        return today > dueDay;
    }
}