package dao.impl;

import dao.BookTranslationDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BookTranslationImpl extends UnicastRemoteObject implements BookTranslationDao {
    private EntityManager em;

    public BookTranslationImpl() throws Exception {
        em = Persistence.createEntityManagerFactory("SQLdb").createEntityManager();

    }
    //    Thống kê số cuốn sách được dịch sang ngôn ngữ khác của từng tác giả, kết quả sắp
//    xếp theo tên tác giả.
//+ countBooksByAuthor(): Map<String, Long>
    @Override
    public Map<String, Long> countBooksByAuthor() throws RemoteException {
        Map<String, Long> result = new LinkedHashMap<>();

        // Correcting the query
        String query ="Select a , count(b) from Book b join b.authors a  group by a";
        List<Object[]> queryResult = em.createQuery(query).getResultList();
        // Iterating through the results and populating the map
        for (Object[] arr : queryResult) {
            String author = (String) arr[0];
            Long count = (Long) arr[1];
            result.put(author, count);
        }

        return result;
    }




}
