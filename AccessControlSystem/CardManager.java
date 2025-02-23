package AccessControlSystem;

import javax.swing.*;
import java.util.HashMap;

public abstract class CardManager {
    protected static final HashMap<String, String> admins = new HashMap<>();
    protected static final HashMap<String, String[]> users = new HashMap<>();

    static {
        admins.put("admin", "admin123");
        users.put("user1", new String[]{"room101", "floor1"});
        users.put("user2", new String[]{"room102", "floor2"});
    }

    public abstract void addCard(String username, String room, String floor);
    public abstract void editCard(String username, String room, String floor);
    public abstract void removeCard(String username);
}

// -------------------- AdminCardManager --------------------
class AdminCardManager extends CardManager {
    @Override
    public void addCard(String username, String room, String floor) {
        users.put(username, new String[]{room, floor});
        JOptionPane.showMessageDialog(null, "เพิ่มผู้ใช้สำเร็จ");
    }

    @Override
    public void editCard(String username, String room, String floor) {
        if (users.containsKey(username)) {
            users.put(username, new String[]{room, floor});
            JOptionPane.showMessageDialog(null, "แก้ไขข้อมูลผู้ใช้สำเร็จ");
        } else {
            JOptionPane.showMessageDialog(null, "ไม่พบผู้ใช้", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void removeCard(String username) {
        if (users.containsKey(username)) {
            users.remove(username);
            JOptionPane.showMessageDialog(null, "ลบผู้ใช้สำเร็จ");
        } else {
            JOptionPane.showMessageDialog(null, "ไม่พบผู้ใช้", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

// -------------------- UserCardManager --------------------
class UserCardManager extends CardManager {
    private final String currentUser;

    public UserCardManager(String currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public void addCard(String username, String room, String floor) {
        JOptionPane.showMessageDialog(null, "ผู้ใช้ทั่วไปไม่สามารถเพิ่มบัตรได้", "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void editCard(String username, String room, String floor) {
        if (currentUser.equals(username)) {
            users.put(username, new String[]{room, floor});
            JOptionPane.showMessageDialog(null, "แก้ไขข้อมูลของคุณสำเร็จ");
        } else {
            JOptionPane.showMessageDialog(null, "คุณสามารถแก้ไขข้อมูลของคุณเองเท่านั้น", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void removeCard(String username) {
        JOptionPane.showMessageDialog(null, "ผู้ใช้ทั่วไปไม่สามารถลบบัตรได้", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

// -------------------- Admin Menu --------------------
class AdminMenu {
    static void showAdminMenu() {
        String[] options = {"เพิ่ม-ลบผู้ใช้", "เพิ่ม-ลบแอดมิน", "แก้ไขข้อมูลบัตรผู้ใช้", "บันทึกข้อมูลการเข้าถึง", "โชว์ข้อมูลทั้งหมด", "ออกจากระบบ"};
        while (true) {
            int choice = JOptionPane.showOptionDialog(null, "เลือกตัวเลือก:", "Admin Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0 -> CardManagement.manageUsers();
                case 1 -> CardManagement.manageAdmins();
                case 2 -> editUserInfo();
                case 3 -> AuditLog.showAccessLogs();  // อ้างอิงคลาสให้ถูกต้อง
                case 4 -> showAllData();
                case 5 -> {
                    return;
                }
            }
        }
    }

    private static void editUserInfo() {
        String username = JOptionPane.showInputDialog("ชื่อผู้ใช้ที่ต้องการแก้ไข:");
        if (CardManager.users.containsKey(username)) {
            String room = JOptionPane.showInputDialog("รหัสห้องใหม่:");
            String floor = JOptionPane.showInputDialog("ชั้นใหม่:");
            CardManager.users.put(username, new String[]{room, floor});
            JOptionPane.showMessageDialog(null, "แก้ไขข้อมูลผู้ใช้สำเร็จ");
        } else {
            JOptionPane.showMessageDialog(null, "ไม่พบผู้ใช้", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void showAllData() {
        StringBuilder sb = new StringBuilder("ข้อมูลผู้ใช้:\n");
        CardManager.users.forEach((key, value) -> sb.append(key).append(" - ห้อง: ").append(value[0]).append(" ชั้น: ").append(value[1]).append("\n"));
        sb.append("\nข้อมูลแอดมิน:\n");
        CardManager.admins.forEach((key, value) -> sb.append(key).append("\n"));
        JOptionPane.showMessageDialog(null, sb.toString(), "ข้อมูลทั้งหมด", JOptionPane.INFORMATION_MESSAGE);
    }
}

// -------------------- Main Menu Navigation --------------------
class Navigation {
    static void showMainMenuAfterAction(JFrame parent) {
        int choice = JOptionPane.showConfirmDialog(parent, "ต้องการกลับไปที่หน้าหลัก?", "กลับไปหน้าหลัก", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            MainMenu.createMainMenu();
            parent.dispose();
        }
    }
}
