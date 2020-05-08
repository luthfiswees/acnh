package message;

import model.Message;

public class MessageProcessor {

    final String DEFAULT_MESSAGE = "What are you talking about?";

    String message;
    Long   chatId;

    public MessageProcessor(Long chatId, String message) {
        this.chatId = chatId;
        this.message = message;
    }

    public Message getMessage() {
        Message finalMessage = null;
        String command = this.message.split(" ")[0];

        switch(command) {
            case "/villager":
                try {
                    finalMessage = new VillagerMessage(this.message).getMessage();
                } catch (Exception e) {
                    finalMessage = new Message("Error fetching villager data. Try again");
                }
                break;
            case "/reminder":
                try {
                    finalMessage = new ReminderMessage(this.chatId, this.message).getMessage();
                } catch (Exception e) {
                    finalMessage = new Message("Error processing reminder. Try again");
                }
                break;
            default:
                finalMessage = new Message(DEFAULT_MESSAGE);
        }

        return finalMessage;
    }
}