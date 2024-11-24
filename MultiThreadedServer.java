import java.net.*;
import java.io.*;
import java.util.*;

public class MultiThreadedServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            System.out.println("Server dang chay...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Ket noi moi tu: " + clientSocket.getInetAddress());
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler extends Thread {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            DataInputStream din = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream dout = new DataOutputStream(clientSocket.getOutputStream());
            while (true) {
                String studentCode = din.readUTF();
                System.out.println("Ma sinh vien nhan duoc: " + studentCode);
                if (studentCode.equalsIgnoreCase("exit")) {
                    System.out.println("Ket thuc ket noi tu: " + clientSocket.getInetAddress());
                    break;
                }
                try {
                    String filteredNumbers = filterNumbers(studentCode);
                    if (filteredNumbers.isEmpty()) {
                        dout.writeUTF("Ma sinh vien khong chua so nao. Vui long nhap lai.");
                        continue;
                    }
                    int evenSum = calculateEvenSum(filteredNumbers);
                    String primeNumbers = findPrimeNumbers(filteredNumbers);
                    dout.writeUTF("Tong cac so chan: " + evenSum);
                    dout.writeUTF("Cac so nguyen to: " + (primeNumbers.isEmpty() ? "Khong co" : primeNumbers));
                } catch (Exception e) {
                    dout.writeUTF("Da xay ra loi: " + e.getMessage());
                }
            }
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String filterNumbers(String studentCode) {
        StringBuilder numbers = new StringBuilder();
        for (char c : studentCode.toCharArray()) {
            if (Character.isDigit(c)) {
                numbers.append(c);
            }
        }
        return numbers.toString();
    }

    private int calculateEvenSum(String numbers) {
        int sum = 0;
        for (char c : numbers.toCharArray()) {
            int digit = c - '0';
            if (digit % 2 == 0) {
                sum += digit;
            }
        }
        return sum;
    }

    private String findPrimeNumbers(String numbers) {
        StringBuilder primes = new StringBuilder();
        for (char c : numbers.toCharArray()) {
            int digit = c - '0';
            if (isPrime(digit)) {
                primes.append(digit).append(" ");
            }
        }
        return primes.toString().trim();
    }

    private boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }
}
