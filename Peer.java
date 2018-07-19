import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Peer {
    public static void main(String[] args) throws UnknownHostException, IOException {
        new Thread(() -> {
            try {
                ServerSocket server = new ServerSocket(3322);
                System.out.println("Server initialized in port 3322");

                Socket client = server.accept();
                System.out.println("Client connected from IP:" + client.getInetAddress().getHostAddress());

                Scanner input = new Scanner(client.getInputStream());

                while (input.hasNextLine()) {
                    System.out.println(input.nextLine());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        Scanner reader = new Scanner(System.in);    
        System.out.print("Type user's IP which you want connect: ");
        Socket client = new Socket(reader.nextLine(), 3322);
        System.out.println("The client connected!");

        PrintStream output = new PrintStream(client.getOutputStream());

        while (reader.hasNextLine()) {
            output.println(reader.nextLine());
        }
    }
}