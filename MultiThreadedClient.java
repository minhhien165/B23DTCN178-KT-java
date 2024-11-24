import java.net.*;
import java.io.*;
import java.util.*;

public class MultiThreadedClient {
    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket("localhost", 9999);
            DataInputStream din = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream dout = new DataOutputStream(clientSocket.getOutputStream());
            Scanner sc = new Scanner(System.in);
            System.out.println("Ket noi den server thanh cong!");
            while (true) {
                System.out.print("Nhap ma sinh vien (hoac nhap 'exit' de thoat): ");
                String studentCode = sc.nextLine();
                dout.writeUTF(studentCode);
                if (studentCode.equalsIgnoreCase("exit")) {
                    System.out.println("Da thoat khoi chuong trinh.");
                    break;
                }
                String evenSumResponse = din.readUTF();
                String primeNumbersResponse = din.readUTF();
                System.out.println(evenSumResponse);
                System.out.println(primeNumbersResponse);
            }
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
