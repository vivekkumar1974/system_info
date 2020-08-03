# System Info Service
Application to create system info and load all the stored system infos 

## How to build and run?

It's a Java maven project. Go to project root directory and run ```$ mvn clean install```. 


### It doesn't work, do I need to install something first?

Yes, you need

- [Java JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) or any other Java 8 version. 
- [Apache Maven 3](https://maven.apache.org/)

### How to set up the service?
- Install [MySql](https://www.mysql.com/)
- Execute the SQL file [create.sql](src/main/resources/create.sql) 
- Add the DB deatils in [systemInfo.properties](src/main/resources/systemInfo.properties)
- run ```$ mvn spring-boot:run```. 
