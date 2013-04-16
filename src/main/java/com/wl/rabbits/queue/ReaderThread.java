package com.wl.rabbits.queue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;

public class ReaderThread extends Thread {
	private final Connection connection;
	private final Object outputSync;
	
	private static int threadCount = 0;
	private final int threadID = threadCount++;
	
    private static final String EXCHANGE = "";
    private static final String QUEUE = "process-queue";
    private static final String ROUTING_KEY = "";

	public ReaderThread(Connection conn, Object oSync) {
		connection = conn;
		outputSync = oSync;
	}

	@Override
	public void run() {
		try {
			while (true) {
				Channel ch = connection.createChannel();
				ch.basicPublish(EXCHANGE, ROUTING_KEY, null, new byte[1024 * 1024]);
				ch.basicConsume(QUEUE, true, "", new DefaultConsumer(ch));
				ch.close();
			}
		} catch (Exception e) {
			synchronized (outputSync) {
				e.printStackTrace();
				System.err.println();
			}
			System.exit(1);
		}
	}

}
