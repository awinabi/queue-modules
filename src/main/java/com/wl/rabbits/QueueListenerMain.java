package com.wl.rabbits;


import com.rabbitmq.client.QueueingConsumer;
import org.json.simple.JSONObject;

public class QueueListenerMain {

    public static void main(String[] argv) throws Exception {

        MessageProcessor mp = new MessageProcessor();
        QueueingConsumer consumer = mp.getConsumer();
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            JSONObject jo = mp.parseJSONString(message);
            System.out.println(" [x] Received Document ~ [" + mp.getDocumentId(jo) + "]");
        }
    }
}
