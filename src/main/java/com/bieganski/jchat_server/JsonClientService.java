package com.bieganski.jchat_server;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class JsonClientService implements Runnable, ClientService {
  private final ConnectionService connectionService;
  private final OutputStream outputStream;
  private final ObjectMapper objectMapper;
  private final Socket socket;
  private String userName;

  public JsonClientService(ConnectionService connectionService, Socket socket) throws IOException {
    this.socket = socket;
    this.connectionService = connectionService;
    outputStream = socket.getOutputStream();

    JsonFactory jsonFactory = new JsonFactory();
    jsonFactory.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
    jsonFactory.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false);
    objectMapper = new ObjectMapper(jsonFactory);
  }

  @Override
  public void run() {
    try {
      Message message = objectMapper.readValue(socket.getInputStream(), Message.class);
      userName = message.getAuthor();
      connectionService.processMsg(this, message);
      while (socket.isConnected()) {
        message = objectMapper.readValue(socket.getInputStream(), Message.class);
        System.out.println("Received: " + message);
        connectionService.processMsg(this, message);
      }
    } catch (IOException e) {
      connectionService.removeClientService(userName);
    }
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
