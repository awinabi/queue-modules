package com.wl.rabbits;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class MessageProcessor {

  private final static String QUEUE_NAME = "process-queue";
  private final static String HOST_NAME = "localhost";
//  private final static String HOST_NAME = "192.168.101.112";
  private final static int PORT = 5672;

  public Connection getConnection() {
    Connection connection = null;

    try {
      ConnectionFactory factory = new ConnectionFactory();
      factory.setHost(HOST_NAME);
      factory.setPort(PORT);
      connection = factory.newConnection();

    } catch (Exception e) {
      System.out.println("Exception in getConnection()");
      e.printStackTrace();
    }
    return connection;
  }

  public QueueingConsumer getConsumer() {
    QueueingConsumer consumer = null;
    try {
      Connection connection = getConnection();
      // connection.
      Channel channel = connection.createChannel();

      channel.queueDeclare(QUEUE_NAME, false, false, false, null);
      System.out.println(" [*] Waiting for messages...");

      consumer = new QueueingConsumer(channel);
      channel.basicConsume(QUEUE_NAME, true, consumer);

    } catch (Exception e) {
      System.out.println("Exception in getQueueConsumer()");
      e.printStackTrace();
    }
    return consumer;
  }

  public JSONObject parseJSONString(String jsonString) {
    JSONParser parser = new JSONParser();
    JSONObject jsonObject = null;
    try {

      Object obj = parser.parse(jsonString);
      jsonObject = (JSONObject) obj;

    } catch (Exception e) {
      e.printStackTrace();
    }
    return jsonObject;
  }

  public String getDocumentId(JSONObject jsonObject) {
    String rootID = null;
    try {
      JSONObject greenCCD = (JSONObject) jsonObject.get("greenCCD");
      JSONObject header = (JSONObject) greenCCD.get("header");
      JSONObject docID = (JSONObject) header.get("documentID");
      rootID = (String) docID.get("root");

    } catch (Exception e) {
      e.printStackTrace();
    }
    return rootID;
  }

  public static void main(String[] argv) throws Exception {

    MessageProcessor mp = new MessageProcessor();
    QueueingConsumer consumer = mp.getConsumer();
    while (true) {
      QueueingConsumer.Delivery delivery = consumer.nextDelivery();
      String message = new String(delivery.getBody());
      JSONObject jo = mp.parseJSONString(message);
      System.out.println(" [x] Received Document ~ ["
          + mp.getDocumentId(jo) + "]");
    }
  }
}
