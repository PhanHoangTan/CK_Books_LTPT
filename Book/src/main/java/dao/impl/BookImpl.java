package dao.impl;

import dao.BookDao;
import entity.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookImpl extends UnicastRemoteObject implements BookDao{
    private EntityManager em;
    public BookImpl() throws RemoteException {
        em = Persistence.createEntityManagerFactory("SQLdb").createEntityManager();
    }

//    Liệt kê danh sách các cuốn sách được viết bởi tác giả nào đó khi biết tên tác giả và
//    có điểm đánh giá từ mấy sao trở lên.
//+ listRatedBooks(author: String, rating: int): List<Book>


    @Override
    public List<Book> listRatedBooks(String author, int rating) throws RemoteException {
        return em.createNamedQuery("Book.listRatedBooks", Book.class)
                .setParameter("rating", rating)
                .setParameter("author", author)
                .getResultList();
    }
    @Override
    public boolean updateReviews(String isbn, String readerID, int rating, String comment) throws RemoteException {
        if (rating < 1 || rating > 5 || comment == null || comment.isEmpty()) {
            return false;
        }
        try {
            em.getTransaction().begin();
            int updatedCount = em.createQuery("UPDATE Reviews r SET r.rating = :rating, r.comment = :comment WHERE r.book.ISBN = :isbn AND r.person.id = :readerID")
                    .setParameter("rating", rating)
                    .setParameter("comment", comment)
                    .setParameter("isbn", isbn)
                    .setParameter("readerID", readerID)
                    .executeUpdate();
            em.getTransaction().commit();
            return updatedCount > 0;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        }
    }



}
