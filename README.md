# SimpleMessageBroker
Simple message broker written in Kotlin
## Installing:
### Using Docker-compose:
Use docker-compose.yml attached to the release. Run:
```bash
docker-compose up
```
### Using Dockerfile:
TODO

### System native (not recommended)
Add new system environments: 
```bash
export TOPICS_DB="path-to-your-topic-database.db"
export BROKER_PORT=5000 #Optional. Default port is 5000
```

Run with java 21:
```bash
java -jar path-to-jar-file.jar
```
## How to use?
See [Documentation](https://github.com/Neitirite/SimpleMessageBroker/blob/main/Documentation.md)
