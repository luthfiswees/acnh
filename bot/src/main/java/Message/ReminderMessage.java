package message;

import java.sql.SQLException;

import model.Message;
import database.Database;

public class ReminderMessage {

    private Message message;
    private Database db;

    public ReminderMessage(Long chatId, String message) throws SQLException {
        this.db = new Database();
        this.message = processMessage(chatId, message);
    }

    public Message getMessage() {
        return this.message;
    }

    private Message processMessage(Long chatId, String message) {
        String command = message.split(" ")[1];
        String name    = capitalize(message.split(" ")[2]);
        String text    = "";

        if (this.db.userNotExist(chatId)) this.db.addUser(chatId);

        switch(command) {
            case "add":
                this.db.addReminder(chatId, name);
                text = "Successfully add reminder for " + name;
                break;
            case "remove":
                this.db.removeReminder(chatId, name);
                text = "Successfully remove reminder for " + name;
                break;
            default:
                text = "There is no such command";
        }

        return new Message(text);
    }

    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}