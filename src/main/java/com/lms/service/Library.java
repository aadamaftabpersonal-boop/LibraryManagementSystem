package com.lms.service;

import com.lms.domain.item.Borrowable;
import com.lms.domain.item.ItemStatus;
import com.lms.domain.item.LibraryItem;
import com.lms.domain.member.Member;

import java.util.Comparator;
import java.util.List;

public class Library{
    private static final Comparator<LibraryItem> BY_ID = Comparator.comparing(LibraryItem::getId);
    private final Catalog catalog = new Catalog();
    private final MemberRegistry members = new MemberRegistry();

    public void addItem(LibraryItem item){
        catalog.add(item);
    }
    public void addMember(Member member){
        members.add(member);
    }
    public List<LibraryItem> inventory(){
        return catalog.all().stream().sorted(BY_ID).toList();
    }
    public ItemStatus statusOf(LibraryItem item){
        if(!(item instanceof Borrowable))return ItemStatus.NOT_BORROWABLE;
        return ItemStatus.AVAILABLE;
    }
}