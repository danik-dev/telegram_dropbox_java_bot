# Telegram Drop-box bot
This is a telegram drop-box bot written with java.

Basic functinality was taken from this youtube [guide](https://youtube.com/playlist?list=PLV_4DSIw2vvI3_a6L_z5AlNaIdFNqQlW2).

## START UP INSTRUCTIONS

### STEP 1

To run the application you must have installed:
* jdk (with configured JAVA_HOME) 
* docker and docker-compose - to build services
* localtunnel from npm - to get static ip for webhooks

### STEP 2
Then you must fill required properties in property-files:

telegranm_dropbox_java_bot/dispatcher/src/main/resources/application.properties 
* bot.name - name of your bot
* bot.token - token from Telegram Bot Father
* bot.uri - static ip that you receive by localtunnel (same port as dispatcher)

Run in terminal to get static ip:
                   
        lt --port 8084
        
telegranm_dropbox_java_bot/node/src/main/resources/application.properties 
* token - same as bot.token

telegranm_dropbox_java_bot/rest-service/src/main/resources/application.properties 
* salt - additional input for id hashing function

telegranm_dropbox_java_bot/mail-service/src/main/resources/application.properties 
* spring.mail.username - email for sending mails to confirm registration
* spring.mail.password - password of that email

### STEP 3
  Then with terminal navigate to the project root directory and run

        docker-compose up

It starts up following services: 
* PostgreSQL database (for data storage)
* PgAdmin - to manage created database
* RabbitMq - to interact between

### STEP 4
After that your should run all microservices starting with dispatcher.

---
Now application is bound to your telegram bot and ready to process user requests.
