package com.lms.service;

import com.lms.domain.exception.MemberNotFoundException;
import com.lms.domain.member.Member;

import java.util.HashMap;
import java.util.Map;

public class MemberRegistry{
    private final Map<String, Member> members = new HashMap<>();
    public void add(Member member){
        members.put(member.getId(), member);
    }
    public Member get(String id){
        Member m = members.get(id);
        if(m==null){
            throw new MemberNotFoundException(id);
        }
        return m;
    }
}