# ECI 2023 - Java Typestate Checker (JaTyC)

JaTyC is a powerful Java source code analysis tool designed to verify adherence to typestates in your Java programs.
This repository contains a specialized application of JaTyC for verifying Java source code that follows a specific protocol involving the interaction between FileClient and FileServer classes. The protocol defines the states, methods, and transitions that can occur during the communication process.

# Protocol Description
The protocol followed by the client (FileClient) and the server (FileServer) in this project is as follows:

1. The client initiates a request by sending the string "REQUEST\n".
2. The client sends the name of the file followed by "\n".
3. If the file exists, the server responds by sending each byte of the file back to the client.
4. If the file does not exist or the end of the file is reached, the server sends the 0 byte.
5. After receiving the 0 byte, the client can initiate a new request by repeating steps 1 and 2 or send the string "CLOSE\n" to conclude the protocol.

# How to Run

1. Compile Your Code: Ensure your Java code for FileClient and FileServer is ready.

2. Run JaTyC: Execute the provided .sh file for JaTyC. If no output is displayed, it indicates that no errors were found during the verification process.

3. Start FileServer: Run the FileServer class to initiate the server and wait for incoming client requests.

4. Start FileClient: Run the FileClient class to start a client that communicates with the server.

5. Graceful Termination: After the client completes its interaction, it will gracefully terminate. You can then terminate the processes by pressing CTRL+C.

# Acknowledgments
This project utilizes the Java Typestate Checker (JaTyC) library. Special thanks for their contribution to the Java development community.

For more information about JaTyC and the specific protocol verification, please refer to the JaTyC GitHub repository. If you have any questions or encounter issues, feel free to create an issue on the repository. Your contributions and feedback are highly appreciated!
