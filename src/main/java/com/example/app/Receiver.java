package com.example.app;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class Receiver {
    private ConnectionFactory factory = null;
    private ActiveMQConnection connection = null;
    private Session session = null;
    private Destination destination = null;
    private MessageConsumer consumer = null;

    public Receiver() {
    }

    public void receiveMessage() {
        try {

            factory = new ActiveMQConnectionFactory(
                    ActiveMQConnection.DEFAULT_BROKER_URL);
            connection = (ActiveMQConnection) factory.createConnection();

            RedeliveryPolicy queueRedeliveryPolicy = connection.getRedeliveryPolicy();
            queueRedeliveryPolicy.setMaximumRedeliveries(0);


            connection.start();
            session = connection.createSession(true, Session.SESSION_TRANSACTED);
            destination = session.createQueue("SAMPLEQUEUE");
            consumer = session.createConsumer(destination);
            int counter = 5;
            while(counter < 10) {

                Message message = consumer.receive();

                if (message instanceof TextMessage) {
                    TextMessage text = (TextMessage) message;
                    System.out.println("Message is : " + text.getText());
                }

                session.rollback();
            }


            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Receiver receiver = new Receiver();
        receiver.receiveMessage();
    }
}

