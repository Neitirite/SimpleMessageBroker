# Simple Message Broker
---
## Documentation for v2.0

### Database

*Each topic has its own table*
#### Topic table structure

| Field        | Type |
| ------------ | ---- |
| Message_UUID | Text |
| Message      | Text |
| Timestamp    | Text |

### Message format

#### Connect to topic
```JSON
{
    "command":"connectTopic",
    "properties":{
    "name":"YourTopicName"
    }
}
```
##### Creates topic if not exists
Creates table "YourTopicName" in database
Returns "200" or SQL error
#### Close topic
```JSON
{
    "command":"closeTopic",
    "properties":{
    "name":"YourTopicName"
	}
}
```
##### Removes  topic if exists
Drops table "YourTopicName" in database
Returns "200" or SQL error

#### Send message
```JSON
{
    "command":"sendMessage",
    "properties":{
        "topic": "YourTopicName",
        "message": { //example JSON message. !MUST BE JSON ELEMENT, NOT STRING!
            "command":"register", 
            "properties":{
            "name":"name",
            "surname":"surname",
            "group":"group",
            "password":"password",
            }
        }
	}
}
```
##### Sends message in topic
Generates UUID for message, gets timestamp and adds a record in table "YourTopicName"
Returns "200" or SQL error
###### Example:

| MESSAGE_UUID                         | MESSAGE                                                                                                       | TIMESTAMP                      |
| ------------------------------------ | ------------------------------------------------------------------------------------------------------------- | ------------------------------ |
| abb5da28-b3e7-416f-8419-05785162a69a | {"command":"register","properties":{"name":"name","surname":"surname","group":"group","password":"password"}} | 2025-07-17T08:52:26.763629389Z |

#### Receive message
```JSON
{
    "command":"receiveMessage",
    "properties":{
	    "topic": "YourTopicName"
    }
}
```
##### Returns you first message from topic or "No messages found" if there are no messages
Selects first record from table "YourTopicName", sends values and drops it
###### Return example:
```JSON
{
	"UUID":"abb5da28-b3e7-416f-8419-05785162a69a",
	"message":"{\"command\":\"register\",\"properties\":{\"name\":\"name\",\"surname\":\"surname\",\"group\":\"group\",\"password\":\"password\"}}"
}
```
Or:
```
No messages found
```

### Environment variables
#### TOPICS_DB
Put here path to database file. File may not exist
#### BROKER_PORT
Specify here the preferred port for the broker