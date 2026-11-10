package com.lms.cli.command;

import com.lms.cli.Command;
import java.util.List;
import com.lms.service.Library;

public class AdvanceCommand implements Command{
    private final Library library;
    public AdvanceCommand(Library library){
        this.library = library;
    }
    @Override
    public List<String> execute(String[] args){
        int days = Integer.parseInt(args[0]);
        int today = library.advance(days);
        return List.of("OK today is day " + today);
    }
}