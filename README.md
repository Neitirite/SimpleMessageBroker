# SimpleMessageBroker
Simple message broker written in Kotlin
# Installing:
## Using Docker-compose:
create docker-compose.yml in your directory with release jar file
```YAML
services:
  kotlin-message-broker:
    image: eclipse-temurin:21-jdk
    container_name: java-app
    volumes:
      - ./path-to-jar-file.jar:/app/path-to-jar-file.jar
    environment:
      - TOPICS_PATH=/app/topics
    command: ["java", "-jar", "/app/path-to-jar-file.jar"]
    ports:
      - "5000:5000"
    restart: unless-stopped
```
## Using Dockerfile:
TODO

## System native (not recommended)
Add new system environment TOPICS_PATH=<path-to-your-topic-directory>

Run with java 21:
```
java -jar <path-to-jar-file.jar>
```
**Note: app uses port 5000, make sure it free to bind**

# Commands:
Connect to the broker via websockets (ws://ip:port), default port is 5000
## Create topic:
### send:
```JSON
{
  "command":"createTopic",
  "properties":{
    "name":"<YourTopicName>"
  }
}
```
### receive:
If topic not exists:
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
### send:
```JSON
{
  "command":"deleteTopic",
  "properties":{
    "name":"<YourTopicName>"
  }
}
```
### receive:
If topic exists:
```
Deleted topic "<YourTopicName>"
```
If topic not exists:
```
Failed to delete "<YourTopicName>": Topic does not exist
```
Else:
You don't receive any message, see error in broker's console

## Send message:
### send:
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

### receive:
If topic exists:
```
Succesfully sent message <YourMessage> in "<YourTopicName>"
```
If topic not exists:
```
Failed to send message in "<YourTopicName>": Topic does not exist
```
Else:
You don't receive any message, see error in broker's console

## Receive message:
### send:
```JSON
{
  "command":"receiveMessage",
  "properties":{
    "topic":"<YourTopicName>"
  }
}
```
### receive:
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
If topic not exists:
```
Failed to receive message from "<YourTopicName": Topic does not exist
```
Else:
You don't receive any message, see error in broker's console
