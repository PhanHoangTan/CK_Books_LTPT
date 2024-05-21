package server;

import dao.BookDao;
import dao.BookTranslationDao;
import dao.impl.BookImpl;
import dao.impl.BookTranslationImpl;
import entity.Book;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.List;

public class Server {
    public Server() {
    }

    public static void main(String[] args) {
        try{
            ServerSocket serverSocket = new ServerSocket(4591);
            try{
                System.out.println("Server is running...");
                while(true){
                    Socket socket = serverSocket.accept();
                    System.out.println("New client connected");
                    System.out.println("Client IP: " + socket.getInetAddress().getHostAddress());
                    Thread t = new Thread(new ClientHandler(socket));
                    t.start();
                }
            } catch (Throwable var5){
                try{
                    serverSocket.close();
                } catch (Throwable var4){
                    var5.addSuppressed(var4);
                }
                throw var5;
            }
    }catch (Exception var6) {
            System.out.println("Server exception " + var6.getMessage());
        }

}}
class ClientHandler implements Runnable {
    private Socket socket;
    private BookDao bookDao;
    private BookTranslationDao bookTranslationDao;

    public ClientHandler(Socket socket) throws Exception {
        this.socket = socket;
        this.bookDao = new BookImpl();
        this.bookTranslationDao = new BookTranslationImpl();
    }

    @Override
    public void run() {
        try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             DataInputStream dis = new DataInputStream(socket.getInputStream())) {
            int choice;
            while (true) {
                try {
                    choice = dis.readInt();
                } catch (EOFException var11) {
                    System.out.println("End of stream reached unexpectedly");
                    return;
                }
                switch (choice) {
                    case 1:
                        String author = dis.readUTF();
                        int rating = dis.readInt();
                        List<Book> books = bookDao.listRatedBooks(author, rating);
                        books.forEach(System.out::println);
                        oos.writeObject(books);
                        oos.flush();
                        break;
                    case 2:
                        oos.writeObject(bookTranslationDao.countBooksByAuthor());
                        break;
                    case 3:
                        String ibsn = dis.readUTF();
                        String readerID = dis.readUTF();
                        int rating2 = dis.readInt();
                        String review = dis.readUTF();
                        boolean result2 = bookDao.updateReviews(ibsn, readerID, rating2, review);
                        oos.writeBoolean(result2);
                        oos.flush();
                        break;
                    default:
                        System.out.println("Invalid choice");
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Client disconnected");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
