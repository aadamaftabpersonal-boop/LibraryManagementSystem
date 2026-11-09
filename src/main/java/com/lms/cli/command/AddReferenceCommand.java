package com.lms.cli.command;

import com.lms.cli.Command;
import com.lms.domain.item.ReferenceBook;
import com.lms.service.Library;

import java.util.List;

public class AddReferenceCommand implements Command{
    private final Library library;
    public AddReferenceCommand(Library library){
        this.library = library;
    }

    @Override 
    public List<String> execute(String[] args){
        library.addItem(new ReferenceBook(args[0], args[1], args[2]));
        return List.of("OK");
    }
}