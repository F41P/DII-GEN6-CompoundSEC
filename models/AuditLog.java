package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AuditLog {

    // รายการเก็บ log
    private static final List<String> logs = new ArrayList<>();

    // รูปแบบเวลาสำหรับ log
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // ฟังก์ชันช่วยสร้างข้อความ log พร้อม timestamp
    private static String createLogMessage(String message) {
        return "[" + LocalDateTime.now().format(formatter) + "] " + message;
    }

    // บันทึกการเปลี่ยนแปลงของการ์ด
    public static void logCardChange(String action, String cardID) {
        logs.add(createLogMessage("Card " + action + ": " + cardID));
    }

    // บันทึกการเปลี่ยนแปลงของแอดมิน
    public static void logAdminChange(String action, String username) {
        logs.add(createLogMessage("Admin " + action + ": " + username));
    }

    // บันทึกเหตุการณ์การเปลี่ยนแปลงทั่วไป
    public static void log(String message) {
        logs.add(createLogMessage(message));
    }

    // ดึงข้อมูล log ทั้งหมด
    public static List<String> getLogs() {
        return new ArrayList<>(logs); // คืนค่ารายการ log แต่ไม่ให้แก้ไข logs ต้นฉบับ
    }

    // แสดง log ทั้งหมด
    public static void showLogs() {
        for (String log : logs) {
            System.out.println(log);
        }
    }

    // บันทึกการเข้าถึงของผู้ใช้
    public static void logUserAccess(String username, String room, String floor) {
        logs.add(createLogMessage("User " + username + " accessed room " + room + " on floor " + floor));
    }

    // บันทึกการกระทำของแอดมิน
    public static void logAdminAction(String adminName, String action) {
        logs.add(createLogMessage("Admin " + adminName + " performed action: " + action));
    }
}