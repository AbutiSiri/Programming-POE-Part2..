package com.mycompany.quickchat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MessageTest {
    @Test
    void messageIdIsAtMostTenDigits() {
        Message message = new Message("+27718693002", "Hello");
        assertEquals(10, message.getMessageID().length());
        assertTrue(message.checkMessageID());
    }

    @Test
    void validRecipientNumberIsAccepted() {
        Message message = new Message("+27718693002", "Hello");
        assertTrue(message.checkRecipientCell());
    }

    @Test
    void invalidRecipientNumberIsRejected() {
        Message message = new Message("0718693002", "Hello");
        assertFalse(message.checkRecipientCell());
    }

    @Test
    void messageUnder250CharactersIsAccepted() {
        Message message = new Message("+27718693002", "Hello, how are you?");
        assertTrue(message.checkMessageLength());
    }

    @Test
    void messageOver250CharactersIsRejected() {
        Message message = new Message("+27718693002", "A".repeat(251));
        assertFalse(message.checkMessageLength());
    }

    @Test
    void sendMessageReturnsSuccessMessage() {
        Message message = new Message("+27718693002", "Hello");
        assertEquals("Message successfully sent.", message.sentMessage());
    }

    @Test
    void disregardMessageReturnsConfirmation() {
        Message message = new Message("+27718693002", "Hello");
        assertEquals("Message disregarded.", message.disregardMessage());
    }

    @Test
    void storeMessageReturnsConfirmationWhenFileIsWritable() {
        Message message = new Message("+27718693002", "Hello");
        assertEquals("Message successfully stored.", message.storeMessage());
    }

    @Test
    void printedMessagesIsNotNull() {
        assertNotNull(Message.printMessages());
    }

    @Test
    void totalMessagesIsNotNegative() {
        assertTrue(Message.returnTotalMessages() >= 0);
    }
}
