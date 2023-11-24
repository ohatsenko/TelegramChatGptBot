<div align="center"><h1>Telegram ChatGPT bot</h1></div>



Below, you'll discover the key elements and functionalities of the
application.

- [Introduction](#introduction)
- [Technologies & Tools](#technologies)
- [Functionalities](#functionalities)
- [Getting Started](#setup)
- [Video Guide](#video-instruction)
- [Testing Tools](#usage-tools)
- [Contribute](#contribute)

<div id="introduction">
    <br>
    <p>
        The Telegram ChatGPT bot is a Java Spring Boot application designed to create a service for receiving responses from OpenAI and managing functionality. This application allows storing Telegram users' questions, ChatGPT responses, viewing user history, and responding to administrators in the chat, all saved in a database
        This README will guide you through using the application and setting it up on your local machine.
    </p>
</div>
<hr>

<div id="technologies">
    <h3> Technologies & Tools </h3>
    <p>
        Our web application is built on Spring Boot and utilizes a range of modern technologies and tools to ensure robustness and scalability, including:
       <br> - Spring Boot Framework for the backend 
       <br> - Spring Security for authentication and security
       <br> - Spring Data JPA for database interaction
       <br> - Swagger for API documentation
       <br> - Maven for library management and project building
       <br> - IntelliJ IDEA as the recommended development environment
       <br> - Docker for running in an isolated environment on different platforms
       <br> - Liquibase for managing database schema changes and revisions 
       <br> - Spring Testing for quality assurance
</p>
</div>
<hr>

<div id="functionalities">
    <h3> Functionalities </h3>
    <p>
        This project features two user roles, ROLE_USER (default) and ROLE_ADMIN, each with their respective capabilities:
        <br>   Users can:
        <br> - View the list of all Telegram bot users.
        <br> - Review the message history of a specific Telegram user.
        <br> - Respond to a specific Telegram user in the chat.
        <br>   Admins can:
        <br> - Has all the functionality of a User.
        <br> - Create users.
        <br> - View the list of users.
        <br> - Delete users
</p>
</div>
<hr>

<div id="setup">
    <h3> Getting Started </h3>
    <p>
        Getting started with this project is a breeze. Follow these steps to set up the project on your local machine:
        <br> Prerequisites:<br>
        <br> Before you begin, make sure you meet the following requirements:
        <br> 0. Install Postman (for making requests to endpoints or use a web browser).
        <br> 1. Install JDK.
        <br> 2. Have PostgreSQL, or another preferred relational database installed.
        <br> 3. Ensure you have Maven for building the project.
        <br> 4. Docker (for running the project in a virtual container).
        <br>Running on your local machine:<br>
        <br> 1. Clone this repository: `git clone https://github.com/ohatsenko/TelegramChatGptBot`.
        <br> 2. Create and configure the .env file to connect to the database before running the app (see example).
        <br> 3. Build the project: `mvn clean install`.
        <br> 4. Run the app: `mvn spring-boot:run`.
        <br>Running with Docker on your machine:<br>
        <br> 0. Install Docker Desktop (Optional): <a href="https://www.docker.com/products/docker-desktop/" target="_blank" class="social-icon">
        https://www.docker.com/products/docker-desktop/</a>
        <br> 1. Run the command to build the Docker image: `docker-compose build`.
        <br> 2. Start the Docker container: `docker-compose up`.
        <br> 3. To stop the containers when needed: `docker-compose down`.
        <br> While the application is running, you can access the Swagger UI for API documentation and testing (Optional):
        <br> 1. Swagger UI URL: <a href="http://localhost:8088/swagger-ui/index.html" target="_blank" class="social-icon">
        http://localhost:8088/swagger-ui/index.html</a>
</p>
</div>
<hr>

<div id="video-instruction">
    <h3> Video Guide </h3>
    <p>
        For a quick visual introduction to our application, check out our short video guide:
        <br> - <a href="https://NULL" target="_blank" class="social-icon">
Video Guide</a>.
    </p>
</div>
<hr>

<div id="usage-tools">
    <h3> Testing Tools </h3>
    <p>
        This project provides an intuitive interface and detailed API documentation through Swagger, which can be accessed at <a href="http://localhost:8088/swagger-ui/index.html" target="_blank" class="social-icon">
        http://localhost:8088/swagger-ui/index.html</a>. 
        Additionally, a collection of Postman requests is available in the root directory of this project.
        `TelegramChatGptBot.postman_collection.json`

By default, a user with the role of admin is already saved in the database,
so you can register as a moderator or log in as an admin using these credentials:

`"email": "admin@mail.com"`

`"password": "adminadmin"`
</p>
</div>
<hr>



<div id="contribute">
    <h3> Contribute </h3>
    <p>
        We welcome contributions! If you'd like to contribute or have any questions, please reach out to us on LinkedIn
    </p>
<a href="https://www.linkedin.com/in/oleksandr-hatsenko/" target="_blank" class="social-icon">
LinkedIn profile</a>

</div>
<hr>
