package model;

public class Message {

    private final String TYPE_TEXT  = "text";
    private final String TYPE_MEDIA = "media";

    private String type;
    private String message;
    private String link;

    public Message(String message) {
        this.type = TYPE_TEXT;
        this.message = message;
    }

    public Message(String message, String link) {
        this.type = TYPE_MEDIA;
        this.message = message;
        this.link = link;
    }

    public boolean isTextMessage() {
        return (this.type.equals(TYPE_TEXT));
    }

    public boolean isMediaMessage() {
        return (this.type.equals(TYPE_MEDIA));
    }

    public String getMessage() {
        return this.message;
    }

    public String getMediaLink() {
        return this.link;
    }
}