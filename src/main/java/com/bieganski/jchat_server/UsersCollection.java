package com.bieganski.jchat_server;

import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

class UsersCollection {
  private HashMap<User, ClientService> users = new HashMap<>();

  void addUser(User user, ClientService clientService) {
    users.put(user, clientService);
  }

  boolean contains(User user) {
    return users.containsKey(user);
  }

  ClientService getService(User user) {
    return users.get(user);
  }

  void executeForEach(BiConsumer<User, ClientService> biConsumer) {
    users.forEach(biConsumer);
  }

  void removeUser(User user) {
    users.remove(user);
  }

  void removeUser(ClientService clientService) {
    users.values().remove(clientService);
    System.out.println(users.size());
  }

  String getAllUsers() {
    return users.keySet().stream()
        .map(User::getName)
        .collect(Collectors.joining(","));
  }
}

