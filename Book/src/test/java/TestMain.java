import dao.BookDao;
import dao.BookTranslationDao;
import dao.impl.BookImpl;
import dao.impl.BookTranslationImpl;
import entity.Book;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestMain {
    static BookDao bookDao;
    static BookTranslationDao bookTranslationDao;


@BeforeAll
    static void setUp() throws Exception {
        bookDao = new BookImpl();
        bookTranslationDao = new BookTranslationImpl();

    }
    @Test
    void testListRatedBooks() throws RemoteException {
        String author = "Richard Helm";
        int rating = 5;
        List<Book> books = bookDao.listRatedBooks(author, rating);
        assertNotNull(books, "listRatedBooks() should not return null");

    }
    @Test
    void testListRatedBooks3() throws RemoteException {
        String author = "Richard Helm";
        int rating = 3;
        List<Book> books = bookDao.listRatedBooks(author, rating);
        System.out.println(books);
    }
    @Test
    void testListRatedBooks2() throws RemoteException {
        String author = "Richard Helm";
        int rating = 3;

        List<Book> actualBooks = bookDao.listRatedBooks(author, rating);
        assertEquals(22, actualBooks.size());
    }
    @Test
    void testCountBooksByAuthor() throws RemoteException {
    bookTranslationDao.countBooksByAuthor().forEach((k, v) -> System.out.println(k + " " + v));

    }
    @Test
    void testCountBooksByAuthor2() throws RemoteException {
        Map<String, Long> expectedCounts = new HashMap<>(); // Add your known counts here
        Map<String, Long> actualCounts = bookTranslationDao.countBooksByAuthor();
        assertEquals(expectedCounts, actualCounts, "countBooksByAuthor() should return the expected counts");
    }
    @Test
    void testUpdateReviews() throws RemoteException  {

        String bookId = "888-0132350800";
        String readedId = "11";
        int rating = 2;
        String review = "Good book for learning javascript";
        boolean result = bookDao.updateReviews(bookId, readedId, rating, review);
        assertEquals(true, result);
    }
    @AfterAll
    static void tearDown() {
        bookDao = null;
    }
}
