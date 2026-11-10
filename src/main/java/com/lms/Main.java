package com.lms;

import com.lms.cli.CommandLoop;
import com.lms.cli.CommandRegistry;
import com.lms.cli.command.AddBookCommand;
import com.lms.cli.command.AddMagazineCommand;
import com.lms.cli.command.AddMemberCommand;
import com.lms.cli.command.AddReferenceCommand;
import com.lms.cli.command.InventoryCommand;
import com.lms.cli.command.BorrowCommand;
import com.lms.cli.command.ReturnCommand;
import com.lms.service.Library;
import com.lms.cli.command.AdvanceCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        Library library = new Library();
        CommandLoop loop = new CommandLoop(buildRegistry(library));
        loop.run(new BufferedReader(new InputStreamReader(System.in)), System.out);
    }

    static CommandRegistry buildRegistry(Library library) {
        CommandRegistry registry = new CommandRegistry();
        registry.register("ADD_BOOK", new AddBookCommand(library));
        registry.register("ADD_MAGAZINE", new AddMagazineCommand(library));
        registry.register("ADD_REFERENCE", new AddReferenceCommand(library));
        registry.register("ADD_MEMBER", new AddMemberCommand(library));
        registry.register("INVENTORY", new InventoryCommand(library));
        registry.register("BORROW", new BorrowCommand(library));
        registry.register("RETURN", new ReturnCommand(library));
        registry.register("ADVANCE", new AdvanceCommand(library));
        return registry;
    }
}