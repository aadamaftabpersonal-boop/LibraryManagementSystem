package com.lms.cli;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommandRegistry{
    private final Map<String, Command> commands = new HashMap<>();
    public void register(String name, Command command){
        commands.put(name, command);
    }
    public Optional<Command> find(String name){
        return Optional.ofNullable(commands.get(name)); 
    }
}