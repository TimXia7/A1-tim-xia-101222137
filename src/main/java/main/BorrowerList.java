package main;

import java.util.ArrayList;

public class BorrowerList {
    ArrayList<Borrower> borrowerList;

    public BorrowerList(){
        borrowerList = new ArrayList<Borrower>();
    }

    public void addBorrower(Borrower borrower){
        borrowerList.add(borrower);
    }

    Borrower getBorrower(int index){
        return borrowerList.get(index);
    }

    public int getSize(){
        return borrowerList.size();
    }

    public void clear(){ borrowerList.clear(); }
}
