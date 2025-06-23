# SimpleMessageBroker
Simple message broker written in Kotlin
# Installing:
## Using Docker-compose:
Create docker-compose.yml in your directory with release jar file
```YAML
services:
  kotlin-message-broker:
    image: eclipse-temurin:21-jdk
    container_name: kotlin-message-broker
    volumes:
      - ./path-to-jar-file.jar:/app/path-to-jar-file.jar
      - ./path-to-your-topic-directory:/app/path-to-your-topic-directory
    environment:
      - TOPICS_PATH=/app/path-to-your-topic-directory
    command: ["java", "-jar", "/app/path-to-jar-file.jar"]
    ports:
      - "5000:5000"
    restart: unless-stopped
```
Run:
```
docker-compose up
```
## Using Dockerfile:
TODO

## System native (not recommended)
Add new system environment: 
```
TOPICS_PATH="<path-to-your-topic-directory>"
```

Run with java 21:
```
java -jar <path-to-jar-file.jar>
```
**Note: app uses port 5000, make sure it free to bind**

# Commands:
Connect to the broker via websockets (ws://ip:port), default port is 5000
## Create topic:
### Send:
```JSON
{
  "command":"createTopic",
  "properties":{
    "name":"<YourTopicName>"
  }
}
```
### Receive:
If topic doesn't exists:
```
Created topic "<YourTopicName>"
```
If topic exists:
```
Joined to the existing topic "<YourTopicName>"
```
Else:

You don't receive any message, see error in broker's console

## Delete topic:
### Send:
```JSON
{
  "command":"deleteTopic",
  "properties":{
    "name":"<YourTopicName>"
  }
}
```
### Receive:
If topic exists:
```
Deleted topic "<YourTopicName>"
```
If topic doesn't exists:
```
Failed to delete "<YourTopicName>": Topic does not exist
```
Else:

You don't receive any message, see error in broker's console

## Send message:
### Send:
```JSON
{
  "command":"sendMessage",
  "properties":{
    "topic":"<YourTopicName>",
    "message":{
      "<YourMessageContentInJSON>":"<YourMessageContentInJSON>"
    }
  }
}
```

**Note: Message content must be JSON object, NOT STRING**

### Receive:
If topic exists:
```
Succesfully sent message <YourMessage> in "<YourTopicName>"
```
If topic doesn't exists:
```
Failed to send message in "<YourTopicName>": Topic does not exist
```
Else:

You don't receive any message, see error in broker's console

## Receive message:
### Send:
```JSON
{
  "command":"receiveMessage",
  "properties":{
    "topic":"<YourTopicName>"
  }
}
```
### Receive:
If topic exists:

If there are messages in the topic:
```
<YourMessage>
```
  **Note: you'll receive message as STRING**

If there are no messages in the topic:
```
There is no messages in topic
```
If topic doesn't exists:
```
Failed to receive message from "<YourTopicName>": Topic does not exist
```
Else:

You don't receive any message, see error in broker's console
