import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import entity.Book;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 4591);
             Scanner scanner = new Scanner(System.in);
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

            System.out.println("Connected to server");
            int choice = 0;
            while (true) {
                System.out.println("1. Cau A");
                System.out.println("2. Cau B");
                System.out.println("3. Cau C");
                System.out.println("4. Exit");

                System.out.println("Enter your choice: ");
                choice = scanner.nextInt();
                dos.writeInt(choice);

                switch (choice) {
                    case 1:
                        scanner.nextLine();
                        System.out.println("Nhập tên tác giả: ");
                        String author = scanner.nextLine();
                        dos.writeUTF(author);

                        System.out.println("Nhập điểm đánh giá: ");
                        int rating = scanner.nextInt();
                        dos.writeInt(rating);
                        scanner.nextLine();
                        dos.flush();

                        List<Book> books = (List<Book>) ois.readObject();
                        books.forEach(System.out::println);
                        break;


                    case 2:
                        // Request for book translation statistics
                        dos.writeInt(2); // Sending the sub-choice for book translation statistics
                        try {
                            // Receive the map from the server
                            Map<String, Long> bookTranslationStatistics = (Map<String, Long>) ois.readObject();

                            // Display the statistics
                            System.out.println("Book Translation Statistics:");
                            for (Map.Entry<String, Long> entry : bookTranslationStatistics.entrySet()) {
                                System.out.println(entry.getKey() + ": " + entry.getValue() + " books translated");
                            }
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 3:
                        scanner.nextLine();
                        System.out.println("Nhập mã sách: ");
                        String bookID = scanner.nextLine();
                        dos.writeUTF(bookID);

                        System.out.println("Nhập mã người đánh giá: ");
                        String reader = scanner.nextLine();
                        dos.writeUTF(reader);

                        System.out.println("Nhập điểm đánh giá: ");
                        int point = scanner.nextInt();
                        dos.writeInt(point);
                        scanner.nextLine();

                        System.out.println("Nhập đánh giá:");
                        String review = scanner.nextLine();
                        dos.writeUTF(review);

                        dos.flush();

                        boolean result2 = ois.readBoolean();
                        if (result2)
                        {
                            System.out.println("Cập nhật thành công");
                        }
                        else
                        {
                            System.out.println("Cập nhật thất bại");
                        }
                        break;
                    case 4:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice");
                        break;
                }
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
