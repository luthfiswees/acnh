package task;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.List;

import model.MessageDetail;

public class Notifier {

    TelegramBot bot;

    public Notifier(TelegramBot bot) {
        this.bot = bot;
    }

    public void notify(List<MessageDetail> messageDetails) {
        for (MessageDetail messageDetail: messageDetails) {
            this.bot.execute(new SendMessage(messageDetail.chatId, generateMessage(messageDetail.villagerName)));
        }
    }

    private String generateMessage(String name) {
        return "Today is " + name + " birthday. Go celebrate with them right now!";
    }
}