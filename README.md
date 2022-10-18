# SDLE First Assignment

SDLE First Assignment of group T06G11.

## **Compile and Run**


#### Installation Dependencies

- [Java](https://www.java.com/) - **Java 18** was used to develop this project
- [Maven](https://maven.apache.org/)

###### Dependencies we used installed by maven

- [jeroMQ](https://github.com/zeromq/jeromq)
- [log4j](https://logging.apache.org/log4j/2.x/)
- [GSON](https://github.com/google/gson)

#### Compile client

```
bash compile.sh
```

#### Run client

###### Put operation

```
bash client.sh put <topic> <path_to_file>
```

###### Get operation

```
bash client.sh get <topic> <client_id>
```

###### Subscribe operation

```
bash client.sh subscribe <topic> <client_id>
```

###### Unsubscribe operation

```
bash client.sh unsubscribe <topic> <client_id>
```

#### Run broker

```
bash broker.sh
```

## **Work distribution**

- [x] Implement a request/reply with JeroMQ, synchronous - Fernando Rego && Rui Moreira
- [x] Find the most suitable pattern for Request/Reply (Lazy Pirate Pattern) - Rui Moreira
- [x] Messages - Fernando Rego
    - [x] Creation of classes and messages for each operation
    - [x] Serialization and deserialization of the messages
- [ ] Create a broker, server like intermediate of communication containing topics
    - [ ] Create broker capable of having multiple topics 
    - [ ] Create topic 
- [ ] Create a mechanism from message UID (hashing the message, and summing the thread ID, to allow sending equal messages but in different requests) 
- [ ] Save state upon process failure:
    - [ ] Make the broker(that as topics) save its state, when the process dies. (Save the state to a file, etc…)
    - [ ] Make the subscriber save, its state, when the process dies (Offset, and messages received) (Save the state to a file, etc…)
    - [ ] Make the publisher save, its state, when the process dies (Offset, and messages received) (Save the state to a file, etc…)
- [ ] Multithreaded writes and reads:
    - [ ] Make the broker, asynchronous, being able to receive messages and write it on the topic and dispatching them to the subscriber upon request
    - [ ] Make the publisher, asynchronous, being able to send multiple messages and handling errors, when a message is not successfully received by the broker
    - [ ] Make the subscriber, able to receive messages and handling errors, when a message is not successfully received by the subscriber
- [ ] Fault Tolerance:
    - [ ] Broker, (no need) just having the need to keep state upon failure
    - [ ] Subscriber, send the message and being able to handle failure and guarantying exactly once (UID, hashing message and thread ID)
    - [ ] Publisher, receive the message and being able to handle failure and guarantying exactly once (Synchronised offsets)
- [ ] If we have time: 
    - [ ] Implement High Water Mark in the broker, to clean topic messages, that are not necessary! (We need to make sure, the ACKS have been received)
- [ ] External Libraries: 
  - [x] Log4j, java logger, GSON

Group members:

1. Bruno Gomes (up201906401@edu.fe.up.pt)
2. Fernando Rego (up201905951@edu.fe.up.pt)
3. José Silva (up201904775@edu.fe.up.pt)
4. Rui Moreira (up201906355@edu.fe.up.pt)
