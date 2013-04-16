Running the mvn project
------------------------------------------
- To build the package run
      mvn package

Executing the jar file
------------------------------------------
- After the jar file is generated run the following for sending and receiving
  messages from the local queue
    - Pumping messages to queue
          java -cp queue-modules-<version>.jar:lib/amqp-client-3.0.4.jar:lib/json-simple-1.1.1.jar com.wl.rabbits.TestSender
    - Receiving messages from queue
          java -cp queue-modules-<version>.jar:lib/amqp-client-3.0.4.jar:lib/json-simple-1.1.1.jar com.wl.rabbits.MessageProcessor
