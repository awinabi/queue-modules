package com.wl.rabbits;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;


public class MessageSender {
    private final static String QUEUE_NAME = "PROCESS-QUEUE";
    private final static String SAMPLE_FILE = "/sample.json";

    private JSONObject parseJSON() {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        try {

            Object obj = parser.parse(new FileReader(MessageSender.class.getResource(SAMPLE_FILE).getPath()));
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

    public String getJSONMessage(JSONObject jsonObject) {
        StringBuffer jsonString = new StringBuffer();
        try {
            jsonString = new StringBuffer(jsonObject.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonString.toString();
    }

    public void pushToQueue(String message) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());

            channel.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void repeatTelecast(String message, int times){

        for (int k=1; k<=times; k++){
            System.out.println("Take "+k);
            pushToQueue(message);
        }
    }


    public static void main(String[] args) {
        MessageSender pm = new MessageSender();
        JSONObject jsonObject = pm.parseJSON();
        String jsonMessage = pm.getJSONMessage(jsonObject);
        System.out.println("Pushing document ~ [" + pm.getDocumentId(jsonObject)+"] to queue ...");
//        pm.pushToQueue(jsonMessage);
        pm.repeatTelecast(jsonMessage, 10);

    }
}
