package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;

import env.Env;
import model.MessageDetail;

public class Database {

    Connection conn;
    String dbName;

    public Database(Env env) {
        this.dbName = env.get("DB_NAME");

        String dbHost = env.get("DB_HOST");
        String dbPort = env.get("DB_PORT");
        String dbUser = env.get("DB_USERNAME");
        String dbPass = env.get("DB_PASSWORD");

        String connUrl = "jdbc:mysql://" + dbHost + ":" + dbPort + "?useUnicode=true&characterEncoding=UTF-8&user=" + dbUser + "&password=" + dbPass;

        try {
            this.conn = DriverManager.getConnection(connUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getVillagersName(String dateString) {
        ResultSet rs;
        List<String> result = new ArrayList<String>();

        try {
            rs = this.conn
                .prepareStatement(DatabaseQuery.getVillagersNameQuery(this.dbName, dateString))
                .executeQuery();

            while(rs.next()) result.add(rs.getString("name"));
        } catch(Exception e) {
            System.out.println("There is an error fetching the query");
            e.printStackTrace();
        }

        return result;
    }

    public List<MessageDetail> getMessageDetail(List<String> villagersName) {
        ResultSet rs;
        List<MessageDetail> messages = new ArrayList<MessageDetail>();

        for (String villagerName : villagersName) {
            try {
                rs = this.conn
                    .prepareStatement(DatabaseQuery.getMessageDetailQuery(this.dbName, villagerName))
                    .executeQuery();

                while(rs.next()) messages.add(new MessageDetail(villagerName, rs.getLong("chat_id")));
            } catch(Exception e) {
                System.out.println("Error occured while fetching data for villager " + villagerName);
                e.printStackTrace();
            }
        }

        return messages;
    }
}