
# Development information

## Building the project
1. Execute the following command in terminal: `mvn clean install spring-boot:repackage` - this will create JAR executable with all bundled dependencies
2. Execute `docker-compose up -d --build` in order to start the compose environment
3. If needed, observe the logs of the containers using the following command: `docker logs <container-name>`. 
    Available containers: *logistics-company-backend*, *logistics-company-database*