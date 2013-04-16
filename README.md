# queue-modules

For sending and receiving data to a rabbitmq queue, using multi-threaded client


## How to Use

### Build the mvn Package

    ~> mvn package

### Running the jar File

After running `mvn package` the jar file dependencies will be in the target/dependency folder

To send messages to the queue run the following in the target directory:

    ~> java -cp queue-modules-1.0.1.jar:dependency/* com.wl.rabbits.TestSender


To receive messages from the queue run the following in the target directory:

    ~> java -cp queue-modules-1.0.1.jar:dependency/* com.wl.rabbits.MessageProcessor


