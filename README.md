# CarrerCrafter_Server

A backend server supporting compiler services alongside assessment, login, and registration functionalities.

## Features

- **Assessment & Authentication**: Endpoints for user login and registration.
- **Compiler API**:
  - **/code/compile**: Accepts code and language, writes the code to disk and compiles/executes it using Docker.
  - **/code/submit**: Submits code along with metadata to MongoDB.
  - **/code/show**: Retrieves historical submissions for a given user and question.
  
## API Flow for Compilation

1. **Code Submission**: The client sends code and the language to the `/code/compile` endpoint.
2. **File Handling**: The server writes the received code to a file (e.g., `main.cpp`, `main.py`, or `main.java`) in the resources directory.
3. **Docker Integration**: 
   - The server uses Docker by running a shell command that compiles (or interprets) the source code.
   - For example, C code is compiled with `g++` and then executed.
4. **Output Capture**: The server captures both standard and error outputs from the Docker process and returns the results.
5. **Persistence**: Submitted code can be stored via the `/code/submit` API and later viewed using `/code/show`.

## Technologies Used

- **Spring Boot** for building RESTful services.
- **MongoDB** for storing code submissions.
- **Docker** for isolated code compilation and execution environments.

----

## Frontend Repository

Explore our frontend code repository for the client-side application:

[CareerForIT Frontend](https://github.com/bkkurandwad/CareerForITFrontend)

----
