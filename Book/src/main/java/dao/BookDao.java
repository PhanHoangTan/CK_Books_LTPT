package dao;

import entity.Book;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface BookDao extends Remote {
    public List<Book> listRatedBooks(String author, int rating) throws RemoteException;
    public boolean updateReviews(String isbn, String readerID, int rating, String comment) throws RemoteException;
}
