package com.example.app;


public class App {

    public static void main(String[] args) throws InterruptedException {
        Sender sender = new Sender();

        Thread.sleep(1);

        sender.sendMessage();
//        sender.sendMessage();
//        sender.sendMessage();
//        sender.sendMessage();
//        sender.sendMessage();
    }
}
