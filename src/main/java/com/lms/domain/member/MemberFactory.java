package com.lms.domain.member;

public final class MemberFactory{
    private MemberFactory(){}
    public static Member create(String type, String id, String name){
        return switch(type){
            case "STUDENT" -> new Student(id, name);
            case "STAFF" -> new Staff(id, name);
            case "FACULTY" -> new Faculty(id, name);
            default -> throw new IllegalArgumentException("unknown member type: " + type);
        };
    }
}