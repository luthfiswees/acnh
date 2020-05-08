package env;

import io.github.cdimascio.dotenv.Dotenv;

public class Env {

    Dotenv env;

    public Env() {
        this.env = Dotenv.load();
    }

    public String get(String key) {
        return this.env.get(key);
    }
}