# SDLE First Assignment

SDLE First Assignment of group T06G11.

## **Work distribution**

- [ ] Implement a pub/sub with JeroMQ, synchronous
- [ ] Find the most suitable pattern for PUB/SUB (Espresso looks ok)
- [ ] Create a broker, server like intermediate of communication containing topics
- [ ] Create a mecanism from message UID (hashing the message, and summing the thread ID, to allow sending equal messages but in different requests) 
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

Group members:

1. Bruno Gomes (up201906401@edu.fe.up.pt)
2. Fernando Rego (up201905951@edu.fe.up.pt)
3. José Silva (up201904775@edu.fe.up.pt)
4. Rui Moreira (up201906355@edu.fe.up.pt)
