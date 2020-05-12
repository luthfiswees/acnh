package task;

import com.pengrad.telegrambot.TelegramBot;

import java.time.LocalDate;
import java.util.List;

import env.Env;
import database.Database;
import model.MessageDetail;

public class NotificationTask implements Runnable {

    TelegramBot bot;
    Env env;

    public NotificationTask(Env env){
        String token = env.get("TELEGRAM_TOKEN");

        this.bot  = new TelegramBot.Builder(token).debug().build();
        this.env  = env;
    }

    public void run() {
        Database db = new Database(this.env);
        List<String> villagersName = db.getVillagersName(getDateString());
        List<MessageDetail> messageDetails = db.getMessageDetail(villagersName);
        
        Notifier notifier = new Notifier(this.bot);
        notifier.notify(messageDetails);
    }

    private String getDateString() {
        LocalDate now = LocalDate.now();
        return now.getMonth().toString() + " " + now.getDayOfMonth();
    }
}