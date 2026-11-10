package com.lms.cli.command;

import com.lms.domain.item.ItemStatus;
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
        ItemStatus status = library.statusOf(item);
        String row = item.getId() + "|" + item.getType() + "|" + status;
        if (status == ItemStatus.ON_LOAN) {
            String memberId = library.findLoan(item.getId()).orElseThrow().getMember().getId();
            row += "|" + memberId;
        }
        return row;
    }
}