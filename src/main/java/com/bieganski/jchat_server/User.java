package com.bieganski.jchat_server;

class User {
  private final String name;

  User(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    return name.equals(((User) o).name);
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return String.format("Username : %s", name);
  }
}
