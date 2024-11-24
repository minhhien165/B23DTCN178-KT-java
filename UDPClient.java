import java.io.*;
import java.net.*;
import java.util.*;
public class UDPClient {
    public static void main(String[] args) {
        try {
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName("localhost");
            Scanner sc = new Scanner(System.in);
            while (true) {
                System.out.print("Nhap so nguyen duong N (hoac nhap 0 de thoat): ");
                String input = sc.nextLine();
                if (input.equals("0")) {
                    System.out.println("Dong ket noi.");
                    break;
                }
                byte[] sendData = input.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, 9999);
                clientSocket.send(sendPacket);
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);
                String serverResponse = new String(receivePacket.getData(), 0, receivePacket.getLength()).trim();
                System.out.println("Phan hoi tu Server: " + serverResponse);
            }
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
