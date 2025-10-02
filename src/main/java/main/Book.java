package main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Book {

    public enum Status {
        AVAILABLE,
        CHECKED_OUT,
        ON_HOLD
    }

    private String title;
    private String author;
    private Status availabilityStatus;
    private String dueDate;
    private BorrowerList holdingList;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.availabilityStatus = Status.AVAILABLE;
        this.holdingList = new BorrowerList();
        this.dueDate = "2025-10-01";
    }
    public String getTitle() { return this.title; }
    public String getAuthor() { return this.author; }
    public Status getAvailabilityStatus() { return this.availabilityStatus; }
    public void setAvailabilityStatus(Status availabilityStatus) { this.availabilityStatus = availabilityStatus; }
    public String getDueDate() { return this.dueDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }
    public void addHolder(Borrower borrower) { holdingList.addBorrower(borrower); }
    public Borrower remove(int index) { return holdingList.removeBorrower(index); }
    public void increaseDueDate(int days) {
        if (this.dueDate == null || this.dueDate.isEmpty()) return;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(this.dueDate, formatter);
        date = date.plusDays(days);
        this.dueDate = date.format(formatter);
    }

}
