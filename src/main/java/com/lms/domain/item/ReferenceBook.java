package com.lms.domain.item;
import java.util.Objects;

public final class ReferenceBook extends LibraryItem{
    private final String author;
    public ReferenceBook(String id, String title, String author){
        super(id, title);
        this.author = Objects.requireNonNull(author, "author");
    }
    public String getAuthor(){
        return this.author;
    }
    @Override
    public ItemType getType(){
        return ItemType.REFERENCE;
    }
}

