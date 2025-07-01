import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Represents a book in the library system
 */
class Book {
    private String bookId;
    private String title;
    private String author;
    private boolean isAvailable;

    public Book(String bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
    }

    // Getters and setters
    public String getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
}

/**
 * Manages the library operations
 */
class Library {
    private ArrayList<Book> books = new ArrayList<>();

    /**
     * Adds a new book to the library
     */
    public void addBook(String bookId, String title, String author) {
        // Check for duplicate book ID
        for (Book b : books) {
            if (b.getBookId().equals(bookId)) {
                JOptionPane.showMessageDialog(null, "Book ID already exists!");
                return;
            }
        }
        books.add(new Book(bookId, title, author));
        JOptionPane.showMessageDialog(null, "Book added successfully!");
    }

    /**
     * Issues a book to a user
     */
    public void issueBook(String bookId) {
        for (Book b : books) {
            if (b.getBookId().equals(bookId)) {
                if (b.isAvailable()) {
                    b.setAvailable(false);
                    JOptionPane.showMessageDialog(null, "Book issued!");
                } else {
                    JOptionPane.showMessageDialog(null, "Book already issued!");
                }
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Book not found!");
    }

    /**
     * Returns a book to the library
     */
    public void returnBook(String bookId) {
        for (Book b : books) {
            if (b.getBookId().equals(bookId)) {
                if (!b.isAvailable()) {
                    b.setAvailable(true);
                    JOptionPane.showMessageDialog(null, "Book returned!");
                } else {
                    JOptionPane.showMessageDialog(null, "Book was not issued!");
                }
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Book not found!");
    }

    /**
     * Gets a list of all available books
     */
    public String getAvailableBooks() {
        StringBuilder sb = new StringBuilder();
        for (Book b : books) {
            if (b.isAvailable()) {
                sb.append("ID: ").append(b.getBookId())
                  .append(", Title: ").append(b.getTitle())
                  .append(", Author: ").append(b.getAuthor()).append("\n");
            }
        }
        return sb.length() > 0 ? sb.toString() : "No books available.";
    }
}

/**
 * Main GUI class for the Library Management System
 */
public class LibraryGUI {
    private static Library library = new Library();

    public static void main(String[] args) {
        // Set up main frame
        JFrame frame = new JFrame("Library Management System");
        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(5, 1, 10, 10));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create buttons
        JButton addBookBtn = new JButton("Add Book");
        JButton issueBookBtn = new JButton("Issue Book");
        JButton returnBookBtn = new JButton("Return Book");
        JButton viewBooksBtn = new JButton("View Available Books");
        JButton exitBtn = new JButton("Exit");

        // Add Book button action
        addBookBtn.addActionListener(e -> {
            JTextField bookId = new JTextField();
            JTextField title = new JTextField();
            JTextField author = new JTextField();
            
            Object[] fields = {
                "Book ID:", bookId,
                "Title:", title,
                "Author:", author
            };
            
            int option = JOptionPane.showConfirmDialog(null, fields, "Add Book", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                library.addBook(bookId.getText(), title.getText(), author.getText());
            }
        });

        // Issue Book button action
        issueBookBtn.addActionListener(e -> {
            String bookId = JOptionPane.showInputDialog("Enter Book ID to issue:");
            if (bookId != null && !bookId.trim().isEmpty()) {
                library.issueBook(bookId);
            }
        });

        // Return Book button action
        returnBookBtn.addActionListener(e -> {
            String bookId = JOptionPane.showInputDialog("Enter Book ID to return:");
            if (bookId != null && !bookId.trim().isEmpty()) {
                library.returnBook(bookId);
            }
        });

        // View Books button action
        viewBooksBtn.addActionListener(e -> {
            String books = library.getAvailableBooks();
            JTextArea textArea = new JTextArea(books);
            textArea.setEditable(false);
            JOptionPane.showMessageDialog(null, new JScrollPane(textArea), 
                "Available Books", JOptionPane.INFORMATION_MESSAGE);
        });

        // Exit button action
        exitBtn.addActionListener(e -> System.exit(0));

        // Add components to frame
        frame.add(addBookBtn);
        frame.add(issueBookBtn);
        frame.add(returnBookBtn);
        frame.add(viewBooksBtn);
        frame.add(exitBtn);

        // Display the frame
        frame.setVisible(true);
    }
}