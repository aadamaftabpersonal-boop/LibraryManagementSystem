package com.lms.domain.item;
import java.util.Objects;

public final class Book extends LibraryItem implements Borrowable{
    private static final int FINE_PER_DAY = 2;
    private final String author;

    public Book(String id, String title, String author){
        super(id, title);
        this.author = Objects.requireNonNull(author, "author");
    }
    public String getAuthor(){
        return this.author;
    }
    @Override
    public ItemType getType(){
        return ItemType.BOOK;
    }
    @Override
    public int getFinePerDay(){
        return FINE_PER_DAY;
    }
}