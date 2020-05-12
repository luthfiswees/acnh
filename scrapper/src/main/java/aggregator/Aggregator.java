package aggregator;

import java.util.List;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import env.Env;
import scrapper.Scrapper;
import villager.Villager;
import villager.VillagerDetail;

public class Aggregator {

    String dbName;
    Env env;
    Connection conn;

    public Aggregator() throws SQLException {
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

    public void fillData(List<Villager> villagers) throws ClassNotFoundException, SQLException {
        ResultSet rs;
        
        databaseSetup();

        for (Villager villager : villagers) {
            fillVillagerData(villager);
        }
    }

    public void databaseSetup() {
        try {
            // Create db if not exists
            this.conn
                .prepareStatement(AggregatorQuery.createDatabase(this.dbName))
                .executeUpdate();

            // use db
            this.conn
                .prepareStatement(AggregatorQuery.useDatabase(this.dbName))
                .executeUpdate();

            // create table villager
            this.conn
                .prepareStatement(AggregatorQuery.CREATE_TABLE_VILLAGER)
                .executeUpdate();

            // create table user
            this.conn
                .prepareStatement(AggregatorQuery.CREATE_TABLE_USER)
                .executeUpdate();
        } catch(Exception e) {
            // print query failure
            System.out.println("///////Database Setup Error///////");
            e.printStackTrace();
        }
    }

    public void fillVillagerData(Villager villager) {
        String name = villager.name;
        String link = villager.link;

        String hubUrl = this.env.get("HUB_URL");

        Scrapper scrpr = new Scrapper(hubUrl);
        VillagerDetail villagerDetail = scrpr.scrapVillagerDetails(name, link);

        insertVillagerData(villagerDetail);
    }

    public void insertVillagerData(VillagerDetail villagerDetail) {
        try {
            // insert villager 
            this.conn
                .prepareStatement(AggregatorQuery.insertVillager(villagerDetail))
                .executeUpdate();
        } catch(Exception e) {
            System.out.println("//////Insert " + villagerDetail.name + " Error//////");
            e.printStackTrace();
        }
    }
}