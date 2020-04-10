package com.bieganski.jchat_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ConnectionListener implements Runnable {

  private final ServerSocket serverSocket;
  private final ConnectionService connectionService;

  public ConnectionListener(ServerSocket serverSocket, ConnectionService connectionService) {
    this.serverSocket = serverSocket;
    this.connectionService = connectionService;
  }

  @Override
  public void run() {
    try {
      while (!serverSocket.isClosed()) {
        Socket socket = serverSocket.accept();
        connectionService.createClientService(socket);
      }
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }
}
