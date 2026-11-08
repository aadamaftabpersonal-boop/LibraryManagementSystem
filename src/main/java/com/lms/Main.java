package com.lms;
import com.lms.domain.item.Book;
import com.lms.domain.item.Borrowable;
import com.lms.domain.item.LibraryItem;
import com.lms.domain.item.Magazine;
import com.lms.domain.item.ReferenceBook;
import com.lms.domain.member.Faculty;
import com.lms.domain.member.Member;
import com.lms.domain.member.Staff;
import com.lms.domain.member.Student;

import java.util.List;

public class Main{
    public static void main(String[] args){
        List<LibraryItem> items = List.of(
                new Book("B1", "Introduction to Algorithms", "Cormen"),
                new Magazine("M1", "Resonance", "Oct 2026"),
                new ReferenceBook("R1", "Mahabharata", "Vyasa"));
        for(LibraryItem i: items){
            String rule = (i instanceof Borrowable b)? "fine per day " + b.getFinePerDay() : "not borrowable";
            System.out.println(i.getId() + " " + i.getType() + " -> " + rule);
        }
        List<Member> members = List.of(
                new Student("S1", "Ananya Rao"),
                new Staff("T1", "Ravi Kumar"),
                new Faculty("F1", "Meera Iyer"));
        for (Member m : members) {
            System.out.println(m.getId() + " " + m.getClass().getSimpleName()
                    + " -> max " + m.getMaxItems()
                    + ", period " + m.getLoanPeriodDays() + " days");
        }
    }
}
