package com.lms.cli.command;

import java.util.List;
import com.lms.domain.member.MemberFactory;
import com.lms.cli.Command;
import com.lms.domain.member.Member;
import com.lms.service.Library;

public class AddMemberCommand implements Command{
    private final Library library;
    public AddMemberCommand(Library library){
        this.library =library;
    }
    @Override
    public List<String> execute(String[] args){
        library.addMember(MemberFactory.create(args[2], args[0], args[1]));
        return List.of("OK");
    }
}