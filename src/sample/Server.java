package sample;

import java.net.*;
import java.io.*;

public class Server {
    Socket socket   = null;
    ServerSocket server   = null;

    public Server(int port)
    {
        try
        {
            server = new ServerSocket(port);
            System.out.println("Server started");

            System.out.println("Waiting for a client ...");

            socket = server.accept();
            System.out.println("Client accepted");

        } catch(IOException i)
        {
            System.out.println(i);
        }
    }

}
