# Meet5 Trial-Task Application
This document contains a brief introduction to what was implemented
in this project.

Throughout this project, the JPA or hibernate weren't used. Instead
it was great experience catching up with good JdbcTemplate after quite 
a while ;). I've added Javadocs and tried to make the code as clean and
understandable as possible.

## Schema Initialization
Class DatabaseInitializer.java is responsible for creating all of the
database schema, tables, indexes etc. using **resource/schema/schema.sql**

It also adds two users as mentioned in **resource/schema/data.sql**

## Visitors 
The endpoint */api/v1/users/{userId}/visitors* handles the visitor functionality.

## Validations
The UserValidator.java class provides static methods for User.java and UserDto.java
classes to validate all of the fields.

## Like/Visit
Relevant endpoints are created.

## Fraudulent Check
This is implemented using Redis.

## Bulk Data
This is handled using batches using PreparedStatements
