package com.mycompany.quickchat;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Message {
    private final String messageID;
    private final String messageHash;
    private final String recipient;
    private final String messageText;

    private static int totalMessages = 0;
    private static final List<Message> sentMessages = new ArrayList<>();

    public Message(String recipient, String messageText) {
        this.messageID = generateMessageID();
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash();
    }

    private String generateMessageID() {
        long number = 1_000_000_000L
                + (long) (new Random().nextDouble() * 9_000_000_000L);
        return String.valueOf(number);
    }

    public boolean checkMessageID() {
        return messageID != null && messageID.matches("\\d{1,10}");
    }

    public boolean checkRecipientCell() {
        return recipient != null && recipient.matches("\\+\\d{10,12}");
    }

    public boolean checkMessageLength() {
        return messageText != null && messageText.length() <= 250;
    }

    private String createMessageHash() {
        String safeText = messageText == null ? "" : messageText.trim();
        if (safeText.isEmpty()) {
            return messageID.substring(0, 2) + ":" + totalMessages + ":";
        }
        String[] words = safeText.split("\\s+");
        String firstWord = words[0].replaceAll("[^a-zA-Z0-9]", "");
        String lastWord = words[words.length - 1].replaceAll("[^a-zA-Z0-9]", "");
        return messageID.substring(0, 2) + ":" + totalMessages + ":"
                + firstWord + lastWord;
    }

    public String sentMessage() {
        sentMessages.add(this);
        totalMessages++;
        return "Message successfully sent.";
    }

    public String disregardMessage() {
        return "Message disregarded.";
    }

    public String storeMessage() {
        try (FileWriter writer = new FileWriter("messages.json", true)) {
            writer.write("{\n");
            writer.write("  \"messageID\": \"" + escape(messageID) + "\",\n");
            writer.write("  \"messageHash\": \"" + escape(messageHash) + "\",\n");
            writer.write("  \"recipient\": \"" + escape(recipient) + "\",\n");
            writer.write("  \"message\": \"" + escape(messageText) + "\"\n");
            writer.write("}\n");
            return "Message successfully stored.";
        } catch (IOException e) {
            return "Error storing message: " + e.getMessage();
        }
    }

    private String escape(String value) {
        if (value == null) return "";
        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }

    public static String printMessages() {
        StringBuilder result = new StringBuilder();
        for (Message message : sentMessages) {
            result.append("Message ID: ").append(message.messageID).append('\n');
            result.append("Message Hash: ").append(message.messageHash).append('\n');
            result.append("Recipient: ").append(message.recipient).append('\n');
            result.append("Message: ").append(message.messageText).append("\n\n");
        }
        return result.toString();
    }

    public static int returnTotalMessages() {
        return totalMessages;
    }

    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
}
