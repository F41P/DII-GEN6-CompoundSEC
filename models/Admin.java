package models;

import controllers.CardManager;

import java.util.HashMap;
import java.util.Map;

public class Admin {
    private String username;
    private String password;
    public static final Map<String, String> admins = new HashMap<>();
    public static final Map<String, Card> users = new HashMap<>();

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // เพิ่มการจัดการ Card ผ่าน CardManager
    public void addCard(Card card) {
        CardManager.addCard(card);  // เพิ่มการจัดการผ่าน CardManager
        AuditLog.logCardChange("Add", card.getCardID());  // บันทึกการเปลี่ยนแปลงใน AuditLog
    }

    // แก้ไขข้อมูลของ Card ผ่าน CardManager
    public void modifyCard(String cardID, String newRoom, String newFloor) {
        Card card = CardManager.getCard(cardID);  // ดึงข้อมูลการ์ดจาก CardManager
        if (card != null) {
            card.setRoom(newRoom);  // แก้ไขข้อมูลห้อง
            card.setFloor(newFloor);  // แก้ไขข้อมูลชั้น
            AuditLog.logCardChange("Modify", cardID);  // บันทึกการเปลี่ยนแปลงใน AuditLog
        }
    }

    // ตรวจสอบความถูกต้องของแอดมิน
    public static boolean isValidAdmin(String username, String password) {
        return admins.getOrDefault(username, "").equals(password);  // ตรวจสอบการล็อกอินแอดมิน
    }

    // เพิ่มแอดมินใหม่
    public static void addAdmin(String username, String password) {
        admins.put(username, password);  // เพิ่มแอดมินในฐานข้อมูล
        AuditLog.logAdminChange("Added", username);  // บันทึกการเพิ่มแอดมินใน AuditLog
    }

    // ลบแอดมิน
    public static void removeAdmin(String username) {
        if (admins.containsKey(username)) {
            admins.remove(username);  // ลบแอดมิน
            AuditLog.logAdminChange("Removed", username);  // บันทึกการลบแอดมินใน AuditLog
        }
    }

    // เพิ่มผู้ใช้
    public static void addUser(String username, Card card) {
        users.put(username, card);  // เพิ่มผู้ใช้ใหม่
        AuditLog.log("Added User: " + username);  // บันทึกการเพิ่มผู้ใช้
    }

    // ลบผู้ใช้
    public static void removeUser(String username) {
        if (users.containsKey(username)) {
            users.remove(username);  // ลบผู้ใช้
            AuditLog.log("Removed User: " + username);  // บันทึกการลบผู้ใช้
        }
    }

    // ดึงข้อมูลผู้ใช้
    public static Map<String, Card> getUsers() {
        return users;  // ส่งกลับข้อมูลผู้ใช้ทั้งหมด
    }

    // การตั้งค่าแอดมินและผู้ใช้เริ่มต้น
    static {
        admins.put("admin", "admin123");  // เพิ่มแอดมินเริ่มต้น
        users.put("user1", new Card("user1", "101", "1", false));  // เพิ่มผู้ใช้เริ่มต้น
    }
}
