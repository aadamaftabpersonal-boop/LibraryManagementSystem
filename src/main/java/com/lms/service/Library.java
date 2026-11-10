package com.lms.service;

import com.lms.domain.exception.ItemNotAvailableException;
import com.lms.domain.item.Borrowable;
import com.lms.domain.item.ItemStatus;
import com.lms.domain.item.LibraryItem;
import com.lms.domain.loan.Loan;
import com.lms.domain.member.Member;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Library {

    private static final Comparator<LibraryItem> BY_ID = Comparator.comparing(LibraryItem::getId);

    private final Catalog catalog = new Catalog();
    private final MemberRegistry members = new MemberRegistry();
    private final LoanRegistry loans = new LoanRegistry();
    private final SimulatedClock clock = new SimulatedClock();

    public void addItem(LibraryItem item) {
        catalog.add(item);
    }

    public void addMember(Member member) {
        members.add(member);
    }

    public Loan borrow(String memberId, String itemId) {
        Member member = members.get(memberId);
        LibraryItem item = catalog.get(itemId);
        if (!(item instanceof Borrowable borrowable)) {
            throw ItemNotAvailableException.notBorrowable(itemId);
        }
        if (loans.isOnLoan(itemId)) {
            throw ItemNotAvailableException.onLoan(itemId);
        }
        member.ensureCanBorrow(loans.countFor(memberId));

        Loan loan = new Loan(borrowable, member, clock.today());
        loans.open(loan);
        return loan;
    }

    public int returnItem(String itemId) {
        catalog.get(itemId);
        Loan loan = loans.close(itemId);
        return loan.fineAsOf(clock.today());
    }

    public List<LibraryItem> inventory() {
        return catalog.all().stream()
                .sorted(BY_ID)
                .toList();
    }

    public ItemStatus statusOf(LibraryItem item) {
        if (!(item instanceof Borrowable)) {
            return ItemStatus.NOT_BORROWABLE;
        }
        return loans.isOnLoan(item.getId()) ? ItemStatus.ON_LOAN : ItemStatus.AVAILABLE;
    }

    public Optional<Loan> findLoan(String itemId) {
        return loans.find(itemId);
    }

    public int today() {
        return clock.today();
    }

    public int advance(int days){
        return clock.advance(days);
    }
}