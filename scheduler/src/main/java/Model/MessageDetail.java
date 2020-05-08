package model;

public class MessageDetail {

    public String villagerName;
    public Long chatId;

    public MessageDetail(String name, Long chatId) {
        this.villagerName = name;
        this.chatId = chatId;
    }
}