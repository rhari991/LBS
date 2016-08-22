package edu.vesit.lbs;

/**
 * Created by Admin on 3/11/2016.
 */
public class Book
{
    private String title, author, book_id;
    private boolean isIssued;

    public Book(String title, String author, boolean isIssued, String book_id)
    {
        this.title = title;
        this.author = author;
        this.isIssued = isIssued;
        this.book_id = book_id;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public boolean getIsIssued()
    {
        return isIssued;
    }

    public void setIsIssued(boolean isIssued)
    {
        this.isIssued = isIssued;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }
}
