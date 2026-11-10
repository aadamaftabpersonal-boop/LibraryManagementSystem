package com.lms.service;

import com.lms.domain.exception.ItemNotOnLoanException;
import com.lms.domain.loan.Loan;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class LoanRegistry{
    private final Map<String, Loan> activeLoans = new HashMap<>();
    public void open(Loan loan){
        String itemid = loan.getItem().getId();
        if(activeLoans.containsKey(itemid)){
            throw new IllegalStateException("item already on loan: " + itemid);
        }
        activeLoans.put(itemid, loan);
    }
    public Loan close(String itemid){
        Loan loan = activeLoans.remove(itemid);
        if(loan==null){
            throw new ItemNotOnLoanException(itemid);
        }
        return loan;
    }
    public boolean isOnLoan(String itemid){
        return activeLoans.containsKey(itemid);
    }
    public Optional<Loan> find(String itemid){
        return Optional.ofNullable(activeLoans.get(itemid));
    }
    public int countFor(String memberid){
        return loansOf(memberid).size();
    }
    public List<Loan> loansOf(String memberid){
        return activeLoans.values().stream().filter(loan->loan.getMember().getId().equals(memberid)).toList();
    }
    public Collection<Loan> all(){
        return Collections.unmodifiableCollection(activeLoans.values());
    }
}