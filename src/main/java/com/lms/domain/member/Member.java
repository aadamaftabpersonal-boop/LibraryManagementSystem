package com.lms.domain.member;
import java.util.Objects;
import com.lms.domain.exception.BorrowLimitExceededException;

public abstract class Member{
    private final String id;
    private final String name;

    protected Member(String id, String name){
        this.id = Objects.requireNonNull(id, "id");
        this.name = Objects.requireNonNull(name, "name");
    }
    public String getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public abstract int getMaxItems();
    public abstract int getLoanPeriodDays();

    @Override
    public boolean equals(Object o){
        if(this==o)return true;
        if(!(o instanceof Member other))return false;
        return id.equals(other.id);
    }
    @Override
    public int hashCode(){
        return id.hashCode();
    }
    public void ensureCanBorrow(int currentLoans){
        if(currentLoans>=getMaxItems()){
            throw new BorrowLimitExceededException(id, getMaxItems());
        }
    }
}