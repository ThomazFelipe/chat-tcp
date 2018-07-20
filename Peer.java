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
                    System.out.println(client.getInetAddress().getHostAddress() + " say: " + input.nextLine());
                }

                input.close();
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        Socket client;
        Scanner reader = new Scanner(System.in);
        System.out.print("Type IP's user which you want connect: ");

        while(true){
            try {
                client = new Socket(reader.nextLine(), 3322);
                System.out.println("The client connected!");

                PrintStream output = new PrintStream(client.getOutputStream());

                while (reader.hasNextLine()) {
                    if (reader.nextLine().equals("/exit")) {
                        System.out.println("Bye!!!");
                        reader.close();
                        client.close();
                        output.close();
                        System.exit(1);
                    }
                    output.println(reader.nextLine());
                }
            } catch (Exception e) {
                System.out.print("Connection failed! Try again: ");
                continue;
            }
        }
    }
}