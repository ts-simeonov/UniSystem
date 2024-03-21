# UniSystem

UniSystem is a simple Java program that simulates a university grading system. It allows for the creation and management of users (students, teachers, and admins) and grades.
  # Table of Contents

    Installation
    Usage
    Contributing
    License

# Installation

To install the project, follow these steps:

    Clone the repository to your local machine.
    Ensure you have Java installed on your machine.
    Compile the Java files in your Java compiler.

# Usage

The system is composed of several classes, each serving a specific purpose:

    User: This is an abstract class that represents a user in the system. It has three subclasses: Student, Teacher and Admin.
    Student: This class extends the User class and represents a student in the system. It includes a list of Grade objects.
    Teacher: This class extends the User class and represents a teacher in the system.
    Admin: This class extends the User class and represents an admin in the system.
    Grade: This class represents a grade in the system. It includes the subject, semester, and mark.
    UserBuilder: This class is used to create new User objects.
    Client: This class represents a client in the system. It is responsible for sending and receiving messages from the server.
    Server: This class represents a server in the system. It is responsible for handling client connections and requests.
    ClientMain: This class includes the main method for the client.
    ServerMain: This class includes the main method for the server.

To use the system, run the ServerMain class to start the server, then run the ClientMain class to start the client.
