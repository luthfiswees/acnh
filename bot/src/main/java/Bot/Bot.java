package bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;

import java.io.IOException;
import java.util.List;
import java.io.File;

import env.Env;
import message.MessageProcessor;
import message.MessageSender;
import model.Message;

public class Bot {
    
    private Env env;
    private final TelegramBot bot;

    public Bot() throws IOException {
        this.env = new Env();
        String token = this.env.get("TELEGRAM_TOKEN");

        bot = new TelegramBot.Builder(token).debug().build();

        bot.setUpdatesListener(new UpdatesListener() {
            @Override
            public int process(List<Update> updates) {

                // process updates
                for (Update update: updates) {
                    Long chatId = update.message().chat().id();
                    String text = update.message().text();

                    // // This one should be useful for future development. I leave it here
                    // Integer senderId = update.message().from().id();
                    // String senderUsername = update.message().from().username();

                    Message message = new MessageProcessor(chatId, text).getMessage();
                    new MessageSender(bot, chatId).send(message);
                }

                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            }
        });
    }
}