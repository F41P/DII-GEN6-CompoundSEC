package AccessControlSystem;

import javax.swing.*;
import java.util.HashMap;

import static AccessControlSystem.AuditLog.showAccessLogs;
import static AccessControlSystem.CardManagement.manageAdmins;
import static AccessControlSystem.CardManagement.manageUsers;
import static AccessControlSystem.MainMenu.createMainMenu;

public class AccessControl {
    public static HashMap<String, String> admins = new HashMap<>();
    public static HashMap<String, String[]> users = new HashMap<>();

    static {
        admins.put("admin", "admin123");
        users.put("user1", new String[]{"room101", "floor1"});
        users.put("user2", new String[]{"room102", "floor2"});

        SwingUtilities.invokeLater(() -> createMainMenu());

    }
    static void showAdminMenu() {
        String[] options = {"เพิ่ม-ลบผู้ใช้", "เพิ่ม-ลบแอดมิน", "แก้ไขข้อมูลบัตรผู้ใช้", "บันทึกข้อมูลการเข้าถึง", "โชว์ข้อมูลทั้งหมด", "ออกจากระบบ"};
        while (true) {
            int choice = JOptionPane.showOptionDialog(null, "เลือกตัวเลือก:", "Admin Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0 -> manageUsers();
                case 1 -> manageAdmins();
                case 2 -> editUserInfo();
                case 3 -> showAccessLogs();
                case 4 -> showAllData();
                case 5 -> {
                    return;
                }
            }
        }
    }


    private static void editUserInfo() {
        String username = JOptionPane.showInputDialog("ชื่อผู้ใช้ที่ต้องการแก้ไข:");
        if (users.containsKey(username)) {
            String room = JOptionPane.showInputDialog("รหัสห้องใหม่:");
            String floor = JOptionPane.showInputDialog("ชั้นใหม่:");
            users.put(username, new String[]{room, floor});
            JOptionPane.showMessageDialog(null, "แก้ไขข้อมูลผู้ใช้สำเร็จ");
        } else {
            JOptionPane.showMessageDialog(null, "ไม่พบผู้ใช้", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void showAllData() {
        StringBuilder sb = new StringBuilder("ข้อมูลผู้ใช้:\n");
        users.forEach((key, value) -> sb.append(key).append(" - ห้อง: ").append(value[0]).append(" ชั้น: ").append(value[1]).append("\n"));
        sb.append("\nข้อมูลแอดมิน:\n");
        admins.forEach((key, value) -> sb.append(key).append("\n"));
        JOptionPane.showMessageDialog(null, sb.toString(), "ข้อมูลทั้งหมด", JOptionPane.INFORMATION_MESSAGE);
    }

    static void showMainMenuAfterAction(JFrame parent) {
        int choice = JOptionPane.showConfirmDialog(parent, "ต้องการกลับไปที่หน้าหลัก?", "กลับไปหน้าหลัก", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            MainMenu.createMainMenu();
            parent.dispose();
        }
    }
}
