package com.bieganski.jchat_server;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Message {
    private int messageType;
    private String author;
    private String date;
    private String message;
    private String receiver;

    private Message() {}

    static class MessageBuilder {
        private int messageType;
        private String author;
        private String receiver;
        private String date;
        private String message;

        MessageBuilder messageType(int id) {
            this.messageType = id;
            return this;
        }

        MessageBuilder author(String author) {
            this.author = author;
            return this;
        }

        MessageBuilder receiver(String receiver) {
            this.receiver = receiver;
            return this;
        }

        MessageBuilder date(String date) {
            this.date = date;
            return this;
        }

        MessageBuilder message(String message) {
            this.message = message;
            return this;
        }

        Message build() {
            Message messageObj = new Message();
            messageObj.messageType = this.messageType;
            messageObj.author = this.author;
            messageObj.receiver = this.receiver;
            messageObj.date = this.date;
            messageObj.message = this.message;
            return messageObj;
        }
    }
}
