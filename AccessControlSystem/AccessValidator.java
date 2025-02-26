package AccessControlSystem;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

// Interface สำหรับ Strategy Pattern
interface AccessStrategy {
    boolean hasAccess(String room, String floor);
}

// Strategy สำหรับชั้น Low
class LowFloorAccessStrategy implements AccessStrategy {
    @Override
    public boolean hasAccess(String room, String floor) {
        return floor.startsWith("L");
    }
}

// Strategy สำหรับชั้น Medium
class MediumFloorAccessStrategy implements AccessStrategy {
    @Override
    public boolean hasAccess(String room, String floor) {
        return floor.startsWith("M");
    }
}

// Strategy สำหรับชั้น High
class HighFloorAccessStrategy implements AccessStrategy {
    @Override
    public boolean hasAccess(String room, String floor) {
        return floor.startsWith("H");
    }
}

// Context ที่ใช้ Strategy Pattern
class AccessContext {
    private AccessStrategy strategy;

    public void setStrategy(AccessStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean executeAccessCheck(String room, String floor) {
        return strategy != null && strategy.hasAccess(room, floor);
    }
}

public class AccessValidator {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final AccessContext accessContext = new AccessContext();

    public static boolean validateUserAccess(String username, String room, String floor) {
        if (!CardManager.users.containsKey(username)) {
            JOptionPane.showMessageDialog(null, "ไม่พบผู้ใช้", "Access Denied", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String[] userDetails = CardManager.users.get(username);
        String userRoom = userDetails[0];
        String userFloor = userDetails[1];

        if (!userRoom.equals(room) || !userFloor.equals(floor)) {
            JOptionPane.showMessageDialog(null, "คุณไม่สามารถเข้าถึงห้องนี้", "Access Denied", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // กำหนด Strategy ตามชั้นของผู้ใช้
        switch (floor.toUpperCase().charAt(0)) {
            case 'L' -> accessContext.setStrategy(new LowFloorAccessStrategy());
            case 'M' -> accessContext.setStrategy(new MediumFloorAccessStrategy());
            case 'H' -> accessContext.setStrategy(new HighFloorAccessStrategy());
            default -> {
                JOptionPane.showMessageDialog(null, "ชั้นไม่ถูกต้อง", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        if (accessContext.executeAccessCheck(room, floor)) {
            String timestamp = LocalDateTime.now().format(formatter);
            AuditLog.logRoomAccess(username, room, floor);
            JOptionPane.showMessageDialog(null, "อนุญาตให้เข้าถึง\nเวลา: " + timestamp, "Access Granted", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "ปฏิเสธการเข้าถึง", "Access Denied", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
