package com.lms.cli.command;

import com.lms.cli.Command;
import com.lms.domain.loan.Loan;
import com.lms.service.Library;

import java.util.List;

public class BorrowCommand implements Command {

    private final Library library;

    public BorrowCommand(Library library) {
        this.library = library;
    }

    @Override
    public List<String> execute(String[] args) {
        Loan loan = library.borrow(args[0], args[1]);
        return List.of("OK due on day " + loan.getDueDay());
    }
}