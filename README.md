# SimpleMessageBroker
Simple message broker written in Kotlin
# Installing:
TODO
# Commands:
Connect to the broker via websockets (ws://ip:port), default port is 5000
## Create topic:
```JSON
{
  "command":"createTopic",
  "properties":{
    "name":"<YourTopicName>"
  }
}
```
