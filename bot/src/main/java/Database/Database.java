package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import env.Env;
import model.Villager;

public class Database {

    String dbName;
    Env env;
    Connection conn;

    public Database() throws SQLException {
        this.env = new Env();

        String dbUser = this.env.get("DB_USERNAME");
        String dbPass = this.env.get("DB_PASSWORD");
        String dbHost = this.env.get("DB_HOST");
        String dbPort = this.env.get("DB_PORT");
        String dbName = this.env.get("DB_NAME");

        String connUrl = "jdbc:mysql://" + dbHost + ":" + dbPort + "?useUnicode=true&characterEncoding=UTF-8&user=" + dbUser + "&password=" + dbPass;

        this.dbName = dbName;
        this.conn = DriverManager.getConnection(connUrl);
    }

    public Villager getVillager(String name){
        ResultSet rs;
        Villager villager = null;

        try {
            rs = this.conn
                .prepareStatement(DatabaseQuery.getVillagerQuery(this.dbName, name))
                .executeQuery();

            while (rs.next()) {
                String vName = rs.getString("name");
                String species = rs.getString("species");
                String gender = rs.getString("gender");
                String personality = rs.getString("personality");
                String birthday = rs.getString("birthday");
                String imageSource = rs.getString("image_source");
                String link = rs.getString("link");

                villager = new Villager(vName, gender, species, personality, birthday, imageSource, link);
            }
        } catch (Exception e) {
            System.out.println("Error occured while fetching data for villager " + name);
        }

        return villager;
    }

    public List<Villager> getVillagerByZodiac(String zodiacName) {
        ResultSet rs;
        List<Villager> villagers = new ArrayList<Villager>();

        try {
            rs = this.conn
                .prepareStatement(DatabaseQuery.getVillagerByZodiacQuery(this.dbName, zodiacName))
                .executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String species = rs.getString("species");
                String gender = rs.getString("gender");
                String personality = rs.getString("personality");
                String birthday = rs.getString("birthday");
                String imageSource = rs.getString("image_source");
                String link = rs.getString("link");

                villagers.add(new Villager(name, gender, species, personality, birthday, imageSource, link));
            }
        } catch (Exception e) {
            System.out.println("Error occured while fetching data for " + zodiacName + " villagers");
        }

        return villagers;
    }

    public List<Villager> getVillagerBySpecies(String speciesName) {
        ResultSet rs;
        List<Villager> villagers = new ArrayList<Villager>();

        try {
            rs = this.conn
                .prepareStatement(DatabaseQuery.getVillagerBySpeciesQuery(this.dbName, speciesName))
                .executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String species = rs.getString("species");
                String gender = rs.getString("gender");
                String personality = rs.getString("personality");
                String birthday = rs.getString("birthday");
                String imageSource = rs.getString("image_source");
                String link = rs.getString("link");

                villagers.add(new Villager(name, gender, species, personality, birthday, imageSource, link));
            }
        } catch (Exception e) {
            System.out.println("Error occured while fetching data for " + speciesName + " villagers");
        }

        return villagers;
    }

    public boolean userNotExist(Long chatId){
        ResultSet rs;
        boolean exist = true;
        List<Long> chatIds = new ArrayList<Long>();

        try {
            rs = this.conn
                .prepareStatement(DatabaseQuery.userNotExistQuery(this.dbName, chatId))
                .executeQuery();

            while (rs.next()) {
                chatIds.add(rs.getLong("chat_id"));
            }

            exist = (chatIds.size() > 0);
        } catch (Exception e) {
            e.printStackTrace();
            exist = false;
        }

        return !exist;
    }

    public void addUser(Long chatId) {
        try {
            this.conn
                .prepareStatement(DatabaseQuery.addUserQuery(this.dbName, chatId))
                .executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addReminder(Long chatId, String name) {
        try {
            String villagers = getVillagers(chatId);

            if (!villagers.contains(name)){
                villagers = villagers + name + ",";

                this.conn
                    .prepareStatement(DatabaseQuery.updateReminderQuery(this.dbName, chatId, villagers))
                    .executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeReminder(Long chatId, String name) {
        try {
            String villagers = getVillagers(chatId);

            if (villagers.contains(name)){
                villagers = villagers.replace(name + ",", "");

                this.conn
                    .prepareStatement(DatabaseQuery.updateReminderQuery(this.dbName, chatId, villagers))
                    .executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getVillagers(Long chatId) {
        ResultSet rs;
        boolean exist = false;
        String villagers = "";

        try {
            rs = this.conn
                .prepareStatement(DatabaseQuery.getVillagersQuery(this.dbName, chatId))
                .executeQuery();

            while (rs.next()) {
                villagers = rs.getString("villagers");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return villagers;
    }
}