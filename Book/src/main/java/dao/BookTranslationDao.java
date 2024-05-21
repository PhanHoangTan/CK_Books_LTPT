package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface BookTranslationDao extends Remote{
    public Map<String,Long> countBooksByAuthor() throws RemoteException;
}
