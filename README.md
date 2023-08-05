NOTES:
This is a not finished telegram drop-box bot written with java.

START UP INSTRUCTIONS

To run the application you must have installed jdk (with configured JAVA_HOME) docker and docker-compose.

* At first you should create your own bot with 'Telegram Bot Father' and put its credentials to dropbox_java_bot/dispatcher/src/main/resources/application.properties 

* Then with terminal navigate to the project root directory and run

        docker-compose up

    It starts up following services: 
    
    -PostgreSQL database (for data storage)
    
    -PgAdmin - to manage created database
    
    -RabbitMq - to interact between


After that your should run despatcher and node microservices.

Now application is bound to your telegram bot
