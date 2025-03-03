package models;

public class User {
    private String name;
    private String room;
    private String floor;
    private boolean isVIP;

    public User(String name, String room, String floor, boolean isVIP) {
        if (!isValidFloor(floor)) {
            throw new IllegalArgumentException("Invalid floor level. Use: low, medium, high.");
        }
        this.name = name;
        this.room = room;
        this.floor = floor.toLowerCase(); // บังคับให้เป็นตัวพิมพ์เล็กเพื่อความสม่ำเสมอ
        this.isVIP = isVIP;
    }

    // Getter
    public String getName() {
        return name;
    }

    public String getRoom() {
        return room;
    }

    public String getFloor() {
        return floor;
    }

    public boolean isVIP() {
        return isVIP;
    }

    // Setter พร้อม Validation
    public void setRoom(String room) {
        this.room = room;
    }

    public void setFloor(String floor) {
        if (!isValidFloor(floor)) {
            throw new IllegalArgumentException("Invalid floor level. Use: low, medium, high.");
        }
        this.floor = floor.toLowerCase();
    }

    public void setVIP(boolean isVIP) {
        this.isVIP = isVIP;
    }

    // ตรวจสอบความถูกต้องของ Floor
    private boolean isValidFloor(String floor) {
        return floor.equalsIgnoreCase("low") ||
                floor.equalsIgnoreCase("medium") ||
                floor.equalsIgnoreCase("high");
    }

    public void showAccessStatus() {
        if (isVIP) {
            System.out.println("VIP access granted. You can edit your card.");
        } else {
            System.out.println("Normal access granted.");
        }
    }

    @Override
    public String toString() {
        return "User{name='" + name + "', room='" + room + "', floor='" + floor + "', isVIP=" + isVIP + "}";
    }
}