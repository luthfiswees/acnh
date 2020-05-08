package message;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.google.common.io.ByteStreams;

import java.io.InputStream;
import java.net.URL;

import model.Message;
import cache.Cache;

public class MessageSender {

    private TelegramBot bot;
    private Long chatId;
    private Cache cache;

    public MessageSender(TelegramBot bot, Long chatId) {
        this.bot = bot;
        this.chatId = chatId;
        this.cache = new Cache();
    }

    public void send(Message message) {
        if (message.isTextMessage()) this.bot.execute(new SendMessage(chatId, message.getMessage()));
        if (message.isMediaMessage()) {
            try {
                this.bot.execute(new SendPhoto(this.chatId, getImage(message)).caption(message.getMessage()));
            } catch (Exception e) {
                this.bot.execute(new SendMessage(this.chatId, e.toString()));
            }
        }
    }

    private byte[] getImage(Message message) throws Exception {
        String link = message.getMediaLink();

        try {
            return this.cache.getByte(link);
        } catch (Exception e) {
            InputStream fin = new URL(link).openStream();
            byte[] img = ByteStreams.toByteArray(fin);

            this.cache.setByte(link, img);
            return img;
        }
    }
}