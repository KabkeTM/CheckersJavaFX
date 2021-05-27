package sample;

import java.net.*;
import java.io.*;

public class Client
{
    Socket socket = null;

    public Client(String address, int port)
    {
        try
        {
            socket = new Socket(address, port);
            System.out.println("Connected");
        } catch(IOException u) {
            System.out.println(u);
        }
    }
} 