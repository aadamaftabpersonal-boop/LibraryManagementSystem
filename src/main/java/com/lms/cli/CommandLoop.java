package com.lms.cli;

import com.lms.domain.exception.LibraryException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Optional;

public class CommandLoop{
    private final CommandRegistry registry;
    public CommandLoop(CommandRegistry registry){
        this.registry = registry;
    }
    public void run(BufferedReader in, PrintStream out) throws IOException{
        String line;
        while((line = in.readLine())!=null){
            line = line.strip();
            if(line.isEmpty()){
                continue;
            }
            String[] parts = line.split("\\|", -1);
            String name = parts[0];
            String[] args = Arrays.copyOfRange(parts,1, parts.length);

            Optional<Command> command = registry.find(name);
            if(command.isEmpty()){
                out.println("ERROR unknown command " + name);
                continue;
            }
            try{
                for(String output: command.get().execute(args)){
                    out.println(output);
                }
            }
            catch(LibraryException e){
                out.println("ERROR " + e.getMessage());
            }
        }
    }
}