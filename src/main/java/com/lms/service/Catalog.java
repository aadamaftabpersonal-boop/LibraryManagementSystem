package com.lms.service;

import com.lms.domain.exception.ItemNotFoundException;
import com.lms.domain.item.LibraryItem;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class Catalog{
    private final Map<String, LibraryItem> items = new HashMap<>();
    public void add(LibraryItem item){
        items.put(item.getId(), item);
    }
    public LibraryItem get(String id){
        LibraryItem item = items.get(id);
        if(item==null){
            throw new ItemNotFoundException(id);
        }
        return item;
    }
    public Collection<LibraryItem> all(){
        return Collections.unmodifiableCollection(items.values());
    }
}