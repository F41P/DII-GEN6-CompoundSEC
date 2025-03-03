package models;

public class Card {
    private String cardID;
    private String room;
    private String floor;
    private boolean isVIP;

    public Card(String cardID, String room, String floor, boolean isVIP) {
        this.cardID = cardID;
        this.room = room;
        this.floor = floor;
        this.isVIP = isVIP;
    }

    public String getCardID() {
        return cardID;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public boolean isVIP() {
        return isVIP;
    }

    public void setVIP(boolean VIP) {
        isVIP = VIP;
    }
}