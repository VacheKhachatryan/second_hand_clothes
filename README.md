# Second-Hand Clothes API

   This project is a Spring Boot application that provides APIs for managing second-hand clothes.

## Prerequisites

- [Java 22+](https://www.oracle.com/java/technologies/downloads/#java23) installed
- [PostgreSQL 16+](https://www.postgresql.org/download/) installed

## Steps to run the application

1. Create the database and schema in your PostgreSQL database by running these commands if you don't already have one for the application to store the data:
   ```sql
   -- Creates the database
   CREATE DATABASE database_name;
   
   -- Connects to that database
   \c database_name; 
   
   -- Creates new schema inside that database
   CREATE SCHEMA IF NOT EXISTS schema_name;
   ```
   Replace `database_name` and `schema_name` with your desired values. 

2. Set the following environment variables with their corresponding values:
    
| Key                          | Description                                                                                                                    | Default Value         |
|------------------------------|--------------------------------------------------------------------------------------------------------------------------------|-----------------------|
| SHC_SECURITY_KEY             | A secure key (minimum length: 32 characters, encoded in Base64 format) that will be used to sign and verify security tokens.   | None                  |
| SHC_ADMIN_USERNAME           | The username of the admin which will be created on application startup.                                                        | None                  |
| SHC_ADMIN_PASSWORD           | The password of the admin which will be created on application startup.                                                        | None                  |
| SHC_DATASOURCE_URL           | Your database url (e.g. 'localhost:5432').                                                                                     | 'localhost:5432'      |
| SHC_DATASOURCE_USERNAME      | Your database username.                                                                                                        | None                  |
| SHC_DATASOURCE_PASSWORD      | Your database password.                                                                                                        | None                  |
| SHC_DATASOURCE_DATABASE_NAME | The name of the existing PostgreSQL database where you want to create the tables.                                              | 'second_hand_clothes' |
| SHC_DATASOURCE_SCHEMA        | The schema name in the database where you want to create the tables.                                                           | 'second_hand_clothes' |

   Learn more about setting environment variables on different OSs [here](https://www3.ntu.edu.sg/home/ehchua/programming/howto/Environment_Variables.html).

3. Build the JAR using the following commands:

   On macOS/Linux
    ```bash
    ./mvnw clean install
    ```
   On Windows:
   ```bash
    mwnw.cmd clean install clean install
   ```

4. Run the JAR using the following command:

    ```bash
    java -jar target/second-hand-clothes-0.0.1-SNAPSHOT.jar
    ```

## Documentation

   To access the API documentation, visit this URL:

   ```http request 
      GET /swagger-ui/index.html
   ```

