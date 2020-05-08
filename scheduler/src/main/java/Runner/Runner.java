package runner;

import it.sauronsoftware.cron4j.Scheduler;

import env.Env;
import task.NotificationTask;

public class Runner {

    Env env;
    Scheduler scheduler;

    public Runner() {
        this.env = new Env();
        this.scheduler = new Scheduler();
    }

    public void run() {
        String crontab = this.env.get("CRONTAB");
        
        NotificationTask nTask = new NotificationTask(this.env);
        
        this.scheduler.schedule(crontab, nTask);
        this.scheduler.start();
    }
}