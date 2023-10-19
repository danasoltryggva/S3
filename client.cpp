//
// Simple client for TSAM-409 Assignment 1
//
// Compile: g++ -Wall -std=c++11 mod_client.cpp
//
// Command line: ./mod_client 127.0.0.1 4000
//
// Author: Dana Sól Tryggvadóttir
//
// Got assistance and a lot of explanation from chat gpt
// Also very new to c++ so syntax and comments might not be the best.


#include <iostream>
#include <cstring>
#include <unistd.h>
#include <arpa/inet.h>
#include <sys/socket.h>

int main(int argc, char *argv[])
{
    int sock;

    // Specifies the format of the input to connect the client to the server
    // Returns instructions if input is wrong
    if (argc != 3)
    {
        std::cerr << "How to use: " << argv[0] << " <server_ip> <server_port>" << std::endl;
        return (-1);
    }

    // Defines the input
    const char *server_ip = argv[1];
    int server_port = atoi(argv[2]);

    // Opens a socket for specified port.
    // Returns -1 if unable to create the socket.
    if ((sock = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP)) < 0)
    {
        perror("Failed to open socket");
        return (-1);
    }

    // Creates a sockaddr_in structure to hold server address information
    // Returns error if server address is invalid
    struct sockaddr_in server_addr;
    memset(&server_addr, 0, sizeof(server_addr));
    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(server_port);
    if (inet_pton(AF_INET, server_ip, &(server_addr.sin_addr)) <= 0)
    {
        perror("Invalid server ip address");
        return (-1);
    }

    // Connects client to the server
    // Returns error if the connection fails
    if (connect(sock, (struct sockaddr *)&server_addr, sizeof(server_addr)) == -1)
    {
        perror("Error connecting to server");
        return (-1);
    }

    // Sends the SYS command to the server
    // Returns error if it fails
    std::string sys_command = "SYS ls\n";
    if (send(sock, sys_command.c_str(), sys_command.length(), 0) == -1)
    {
        perror("Error sending command to server");
        return (-1);
    }

    // Receive and print the server's response (if any)
    char buffer[1024];
    int bytes_received = recv(sock, buffer, sizeof(buffer), 0);
    if (bytes_received > 0)
    {
        buffer[bytes_received] = '\0';
        std::cout << "Server response: " << buffer << std::endl;
    }
    else if (bytes_received == 0)
    {
        std::cout << "Server closed the connection." << std::endl;
    }
    else
    {
        perror("Receive error");
    }

    // Close the client socket
    close(sock);

    return 0;
}
