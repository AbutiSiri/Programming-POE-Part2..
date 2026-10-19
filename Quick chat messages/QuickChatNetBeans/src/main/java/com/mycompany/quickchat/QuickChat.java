package com.mycompany.quickchat;

import java.util.Scanner;

public class QuickChat {
    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            // Replace this demonstration flag with your real Login class.
            boolean loggedIn = true;

            if (!loggedIn) {
                System.out.println("Please log in before using QuickChat.");
                return;
            }

            System.out.println("Welcome to QuickChat!");
            System.out.print("How many messages would you like to send? ");
            int messageLimit = readNumber(input);
            int messagesProcessed = 0;
            boolean running = true;

            while (running) {
                System.out.println("\\n1. Send Messages");
                System.out.println("2. Show recently sent messages");
                System.out.println("3. Quit");
                System.out.print("Choose an option: ");
                String choice = input.nextLine().trim();

                switch (choice) {
                    case "1":
                        if (messagesProcessed >= messageLimit) {
                            System.out.println("You have reached your message limit.");
                            break;
                        }

                        System.out.print("Enter recipient number (e.g. +27718693002): ");
                        String recipient = input.nextLine().trim();
                        System.out.print("Enter your message (maximum 250 characters): ");
                        String text = input.nextLine();

                        Message message = new Message(recipient, text);
                        if (!message.checkMessageID()) {
                            System.out.println("Invalid message ID.");
                            break;
                        }
                        if (!message.checkRecipientCell()) {
                            System.out.println("Recipient number is incorrectly formatted.");
                            break;
                        }
                        if (!message.checkMessageLength()) {
                            System.out.println("Please enter a message of 250 characters or fewer.");
                            break;
                        }

                        System.out.println("Message ID: " + message.getMessageID());
                        System.out.println("Message Hash: " + message.getMessageHash());
                        System.out.println("1. Send Message");
                        System.out.println("2. Disregard Message");
                        System.out.println("3. Store Message to send later");
                        System.out.print("Choose an option: ");
                        String action = input.nextLine().trim();

                        switch (action) {
                            case "1":
                                System.out.println(message.sentMessage());
                                messagesProcessed++;
                                break;
                            case "2":
                                System.out.println(message.disregardMessage());
                                messagesProcessed++;
                                break;
                            case "3":
                                System.out.println(message.storeMessage());
                                messagesProcessed++;
                                break;
                            default:
                                System.out.println("Invalid option.");
                        }
                        break;
                    case "2":
                        String sent = Message.printMessages();
                        System.out.println(sent.isEmpty() ? "Coming Soon." : sent);
                        break;
                    case "3":
                        running = false;
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }

            System.out.println("Messages successfully sent: " + Message.returnTotalMessages());
        }
    }

    private static int readNumber(Scanner input) {
        while (true) {
            try {
                int number = Integer.parseInt(input.nextLine().trim());
                if (number >= 0) return number;
                System.out.print("Enter zero or a positive number: ");
            } catch (NumberFormatException e) {
                System.out.print("Enter a valid whole number: ");
            }
        }
    }
}
