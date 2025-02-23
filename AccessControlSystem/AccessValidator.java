package AccessControlSystem;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static AccessControlSystem.AuditLog.logRoomAccess;
import static AccessControlSystem.CardManager.users;

public class AccessValidator {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static boolean validateUserAccess(String username, String room, String floor) {
        if (!users.containsKey(username)) {
            JOptionPane.showMessageDialog(null, "ไม่พบผู้ใช้", "Access Denied", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        //ใช้ Abstract Class AccessLevel
        AccessLevel accessLevel;
        switch (floor.toUpperCase().charAt(0)) {
            case 'L' -> accessLevel = new LowFloorAccess();
            case 'M' -> accessLevel = new MediumFloorAccess();
            case 'H' -> accessLevel = new HighFloorAccess();
            default -> {
                JOptionPane.showMessageDialog(null, "ชั้นไม่ถูกต้อง", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        if (accessLevel.hasAccess(room, floor)) {
            String timestamp = LocalDateTime.now().format(formatter);
            logRoomAccess(username, room, floor);
            JOptionPane.showMessageDialog(null, "อนุญาตให้เข้าถึง\nเวลา: " + timestamp, "Access Granted", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "ปฏิเสธการเข้าถึง", "Access Denied", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
