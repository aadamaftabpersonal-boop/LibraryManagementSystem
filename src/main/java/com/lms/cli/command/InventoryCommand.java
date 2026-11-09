package com.lms.cli.command;

import com.lms.cli.Command;
import com.lms.cli.ReportFormatter;
import com.lms.domain.item.LibraryItem;
import com.lms.service.Library;

import java.util.List;

public class InventoryCommand implements Command {

    private final Library library;

    public InventoryCommand(Library library) {
        this.library = library;
    }

    @Override
    public List<String> execute(String[] args) {
        List<String> rows = library.inventory().stream()
                .map(this::formatRow)
                .toList();
        return ReportFormatter.rowsOrNone(rows);
    }

    private String formatRow(LibraryItem item) {
        return item.getId() + "|" + item.getType() + "|" + library.statusOf(item);
    }
}