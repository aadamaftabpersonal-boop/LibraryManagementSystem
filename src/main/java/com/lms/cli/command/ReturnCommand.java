package com.lms.cli.command;

import com.lms.cli.Command;
import com.lms.service.Library;

import java.util.List;

public class ReturnCommand implements Command {

    private final Library library;

    public ReturnCommand(Library library) {
        this.library = library;
    }

    @Override
    public List<String> execute(String[] args) {
        int fine = library.returnItem(args[0]);
        return List.of("OK fine " + fine);
    }
}