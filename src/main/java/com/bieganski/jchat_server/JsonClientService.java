package com.bieganski.jchat_server;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class JsonClientService implements Runnable, ClientService {
  private final DataOutputStream outToClient;
  private final OutputStream outputStream;
  private final ObjectMapper objectMapper;
  private final Socket socket;
  private final ConnectionService connectionService;

  public JsonClientService(ConnectionService connectionService, Socket socket) throws IOException {
    this.socket = socket;
    this.connectionService = connectionService;
    outToClient = new DataOutputStream(this.socket.getOutputStream());
    outputStream = socket.getOutputStream();

    JsonFactory jsonFactory = new JsonFactory();
    jsonFactory.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
    jsonFactory.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false);
    objectMapper = new ObjectMapper(jsonFactory);
  }

  @Override
  public void run() {
    try {
      while (socket.isConnected()) {
        Message message = objectMapper.readValue(socket.getInputStream(), Message.class);
        System.out.println("Received: " + message);
        connectionService.processMsg(this, message);
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  @Override
  public void sendMessage(String msg) {
    new Thread(() -> {
      try {
        Message message = new Message.MessageBuilder().message(msg).build();
        objectMapper.writeValue(outputStream, message);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }).start();
  }

  @Override
  public void sendMessage(Message message) {
    try {
      objectMapper.writeValue(outputStream, message);
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }
}
