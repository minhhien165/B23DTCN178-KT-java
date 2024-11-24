import java.io.*;
import java.net.*;
import java.util.*;
public class UDPServer {
    public static void main(String[] args) {
        try {
            DatagramSocket serverSocket = new DatagramSocket(9999);
            System.out.println("Server dang chay...");
            while (true) {
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                String clientMessage = new String(receivePacket.getData(), 0, receivePacket.getLength()).trim();
                System.out.println("Nhan tu Client: " + clientMessage);
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();
                int n;
                String response;
                try {
                    n = Integer.parseInt(clientMessage);
                    if (n <= 0) {
                        response = "N khong hop le. Vui long nhap mot so nguyen duong.";
                    } else {
                        response = findPrimesDivisibleBy5(n);
                    }
                } catch (NumberFormatException e) {
                    response = "Du lieu khong hop le. Vui long nhap so nguyen.";
                }
                byte[] sendData = response.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                serverSocket.send(sendPacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String findPrimesDivisibleBy5(int n) {
        StringBuilder result = new StringBuilder("Các số nguyên tố nhỏ hơn " + n + " và chia hết cho 5: ");
        boolean hasPrimes = false;
        for (int i = 2; i < n; i++) {
            if (isPrime(i) && i % 5 == 0) {
                result.append(i).append(" ");
                hasPrimes = true;
            }
        }
        if (!hasPrimes) {
            return "Không có số nguyên tố nhỏ hơn " + n + " chia hết cho 5.";
        }
        return result.toString().trim();
    }

    private static boolean isPrime(int num) {
        if (num < 2) return false;
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) return false;
        }
        return true;
    }
}
