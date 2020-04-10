package com.bieganski.jchat_server;

import java.io.IOException;
import java.net.Socket;

class ConnectionService {
  private final UsersCollection usersCollection;

  public ConnectionService(UsersCollection usersCollection) {
    this.usersCollection = usersCollection;
  }

  void createClientService(Socket socket) {
    try {
      new Thread(new JsonClientService(this, socket), "User thread").start();
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }

  void processMsg(ClientService clientService, Message message) {
    switch (message.getMessageType()) {
      case 0:
        if (message.getReceiver() != null) {
          User receiver = new User(message.getReceiver());
          if (usersCollection.contains(receiver)) {
            usersCollection.getService(receiver).sendMessage(message);
          }
        }
        break;
      case 1:
        usersCollection.addUser(new User(message.getAuthor()), clientService);
        usersCollection.executeForEach((k, v) -> v.sendMessage(message));
        break;
    }
  }
  void removeClientService(ClientService clientService){

  }
}
