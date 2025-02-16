package AccessControlSystem;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static AccessControlSystem.AccessControl.users;
import static AccessControlSystem.AuditLog.logAccess;

public class AccessValidator {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public interface IAccessValidator {
        boolean validateUserAccess(String username, String room, String floor);
    }

    public static boolean validateUserAccess(String username, String room, String floor) {
        if (users.containsKey(username) && users.get(username)[0].equals(room) && users.get(username)[1].equals(floor)) {
            String timestamp = LocalDateTime.now().format(formatter);
            logAccess(username, room, floor);
            JOptionPane.showMessageDialog(null, "อนุญาตให้เข้าถึง\nเวลา: " + timestamp, "Access Granted", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "ปฏิเสธการเข้าถึง", "Access Denied", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
