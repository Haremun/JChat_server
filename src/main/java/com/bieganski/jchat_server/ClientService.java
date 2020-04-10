package com.bieganski.jchat_server;

interface ClientService {
  void sendMessage(String message);

  void sendMessage(Message message);
}
