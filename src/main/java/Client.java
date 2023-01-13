import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final int PORT = 8989;
    private static String LOCAL_HOST = "127.0.0.1";

    public static void main(String[] args) throws IOException {
        try (Socket clientSocket = new Socket(LOCAL_HOST, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            System.out.println("Введите искомое слово:");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            out.println(input);
            System.out.println(in.readLine());
        }
    }
}
