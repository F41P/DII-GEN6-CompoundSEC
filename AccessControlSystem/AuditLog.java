package AccessControlSystem;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AuditLog {
    static final ArrayList<String> accessLogs = new ArrayList<>();
    static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    // Interface สำหรับการบันทึกการเข้าถึง
    public static void logRoomAccess(String username, String room, String floor) {
        String timestamp = LocalDateTime.now().format(formatter);
        String logEntry = timestamp + " - " + username + " เข้าถึงห้อง " + room + " ชั้น " + floor;
        accessLogs.add(logEntry);
    }

    // บันทึกการเข้าถึงทั่วไป
    public static void logEvent(String username, String action, String details) {
        String timestamp = LocalDateTime.now().format(formatter);
        String logEntry = timestamp + " - " + username + " " + action + " (" + details + ")";
        accessLogs.add(logEntry);
    }

    public static void showAccessLogs() {
        JOptionPane.showMessageDialog(null, String.join("\n", accessLogs), "บันทึกการเข้าถึง", JOptionPane.INFORMATION_MESSAGE);
    }
}
