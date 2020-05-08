package runner;

import java.util.List;

import it.sauronsoftware.cron4j.Scheduler;

import env.Env;
import scrapper.Scrapper;
import villager.Villager;
import aggregator.Aggregator;

public class Runner {

    Env env;

    public Runner() {
        this.env = new Env();
    }

    public void run() {
        Scheduler s = new Scheduler();
        String cronString = this.env.get("CRON_STRING");

        s.schedule(cronString, new Runnable() {
			public void run() {
				exec();
			}
		});
        s.start();
    }

    public void exec() {
        Scrapper scrapper = new Scrapper(this.env.get("CHROMEDRIVER_PATH"));
        List<Villager> villagers = scrapper.scrapVillagerNames();
        
        try {
            Aggregator agg = new Aggregator();
            agg.fillData(villagers);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}