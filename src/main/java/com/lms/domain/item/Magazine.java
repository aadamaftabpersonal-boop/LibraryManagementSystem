package com.lms.domain.item;
import java.util.Objects;

public final class Magazine extends LibraryItem implements Borrowable{
    private static final int FINE_PER_DAY = 1;
    private final String issue;

    public Magazine(String id, String title, String issue){
        super(id, title);
        this.issue = Objects.requireNonNull(issue, "issue");
    }

    public String getIssue(){
        return this.issue;
    }

    @Override
    public ItemType getType(){
        return ItemType.MAGAZINE;
    }

    @Override
    public int getFinePerDay(){
        return FINE_PER_DAY;
    }
}