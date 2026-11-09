package com.lms.cli;
import java.util.List;

public interface Command{
    List<String> execute(String[] args);
}