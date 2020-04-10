package com.bieganski.jchat_server;

import java.io.IOException;
import java.net.ServerSocket;

public class App {
  public static void main(String[] args) throws IOException {
    UsersCollection usersCollection = new UsersCollection();
    ConnectionService connectionService = new ConnectionService(usersCollection);
    new Thread(new ConnectionListener(new ServerSocket(8090), connectionService),
        "Connection listener thread").start();
  }
}
