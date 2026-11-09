package com.lms.cli;

import java.util.List;

public final class ReportFormatter{
    private ReportFormatter(){}
    public static List<String> rowsOrNone(List<String> rows){
        return rows.isEmpty() ? List.of("NONE"): rows;
    }
}