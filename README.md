# queue-modules

For sending and receiving data to a rabbitmq queue, using multi-threaded client


## How to Use

### Build the mvn Package

    ~> mvn package

### Running the jar File

This assumes the dependent jar files are in the lib folder [amqp-client-3.0.4.jar, json-simple-1.1.1.jar ]

To send messages to the queue run the following :

    ~> java -cp queue-modules-<version>.jar:lib/amqp-client-3.0.4.jar:lib/json-simple-1.1.1.jar com.wl.rabbits.TestSender


To receive messages from the queue run the following :

    ~> java -cp queue-modules-<version>.jar:lib/amqp-client-3.0.4.jar:lib/json-simple-1.1.1.jar com.wl.rabbits.MessageProcessor


