package com.lms.domain.item;
import java.util.Objects;

public abstract class LibraryItem{
    private final String id;
    private final String title;
    protected LibraryItem(String id, String title){
        this.id = Objects.requireNonNull(id, "id");
        this.title = Objects.requireNonNull(title, "title");
    }
    public String getId(){
        return this.id;
    }
    public String getTitle(){
        return this.title;
    }
    public abstract ItemType getType();
    @Override 
    public boolean equals(Object o){
        if(this==o)return true;
        if(!(o instanceof LibraryItem other))return false;
        return this.id.equals(other.getId());
    }
    @Override 
    public int hashCode(){
        return id.hashCode();
    }
    @Override
    public String toString(){
        return getType() + " " + id + " \"" + title + "\"";
    }
}