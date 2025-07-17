# SimpleMessageBroker
Simple message broker written in Kotlin
## Installing:
### Using Docker-compose:
Use docker-compose.yml attached to the release
Run:
```
docker-compose up
```
### Using Dockerfile:
TODO

### System native (not recommended)
Add new system environment: 
```
TOPICS_DB="<path-to-your-topic-database.db>"
```

Run with java 21:
```
java -jar <path-to-jar-file.jar>
```
**Note: app uses port 5000, make sure it free to bind**
## How to use?
See [Documentation](https://github.com/Neitirite/SimpleMessageBroker/blob/main/Documentation.md)
