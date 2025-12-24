import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class LibraryApp {

    private List<Book> books;
    private Scanner scanner;

    public LibraryApp() {
        books = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void run() {
        boolean running = true;

        System.out.println("Welcome to Library App!");

        while (running) {
            printMenu();
            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1:
                    printAllBooks();
                    break;
                case 2:
                    addNewBook();
                    break;
                case 3:
                    searchBooksByTitle();
                    break;
                case 4:
                    borrowBook();
                    break;
                case 5:
                    returnBook();
                    break;
                case 6:
                    deleteBookById();
                    break;
                case 7:
                    running = false;
                    System.out.println("Exiting application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1-7.");
            }

            System.out.println();
        }

        scanner.close();
    }

    private void printMenu() {
        System.out.println("1. Print all books");
        System.out.println("2. Add new book");
        System.out.println("3. Search books by title");
        System.out.println("4. Borrow a book");
        System.out.println("5. Return a book");
        System.out.println("6. Delete a book by id");
        System.out.println("7. Quit");
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine();
            try {
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private int readYear(String prompt) {
        while (true) {
            int year = readInt(prompt);
            try {
                // simple validation via temporary Book
                int currentYear = java.time.Year.now().getValue();
                if (year < 1500 || year > currentYear) {
                    System.out.println("Year must be between 1500 and " + currentYear);
                } else {
                    return year;
                }
            } catch (Exception e) {
                System.out.println("Invalid year: " + e.getMessage());
            }
        }
    }

    private String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine();
            if (line != null && !line.trim().isEmpty()) {
                return line.trim();
            }
            System.out.println("Input must not be empty.");
        }
    }

    private void printAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in the library");
        } else {
            for (Book b : books) {
                System.out.println(b);
            }
        }
    }

    private void addNewBook() {
        String title = readNonEmptyString("Enter title: ");
        String author = readNonEmptyString("Enter author: ");
        int year = readYear("Enter year: ");

        try {
            Book book = new Book(title, author, year);
            books.add(book);
            System.out.println("Book added: " + book);
        } catch (IllegalArgumentException e) {
            System.out.println("Error creating book: " + e.getMessage());
        }
    }

    private void searchBooksByTitle() {
        String part = readNonEmptyString("Enter part of the title: ");
        String query = part.toLowerCase(Locale.ROOT);

        boolean found = false;
        for (Book b : books) {
            if (b.getTitle() != null &&
                    b.getTitle().toLowerCase(Locale.ROOT).contains(query)) {
                System.out.println(b);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No books found with that title.");
        }
    }

    private Book findBookById(int id) {
        for (Book b : books) {
            if (b.getId() == id) {
                return b;
            }
        }
        return null;
    }

    private void borrowBook() {
        int id = readInt("Enter book id to borrow: ");
        Book book = findBookById(id);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        if (book.isAvailable()) {
            book.markAsBorrowed();
            System.out.println("Book borrowed: " + book);
        } else {
            System.out.println("Book is already borrowed.");
        }
    }

    private void returnBook() {
        int id = readInt("Enter book id to return: ");
        Book book = findBookById(id);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        if (!book.isAvailable()) {
            book.markAsReturned();
            System.out.println("Book returned: " + book);
        } else {
            System.out.println("Book is not borrowed.");
        }
    }

    private void deleteBookById() {
        int id = readInt("Enter book id to delete: ");
        Book book = findBookById(id);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }
        books.remove(book);
        System.out.println("Book deleted: " + book);
    }

    public static void main(String[] args) {
        LibraryApp app = new LibraryApp();
        app.run();
    }
}
