package AccessControlSystem;

import javax.swing.*;
import java.awt.*;

import static AccessControlSystem.AccessControl.*;
import static AccessControlSystem.AuditLog.*;
import static AccessControlSystem.CardManagement.manageAdmins;
import static AccessControlSystem.CardManagement.manageUsers;

public class MainMenu {
    public static void createMainMenu() {
        JFrame frame = new JFrame("Access Control System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setLayout(new GridLayout(3, 1));

        JButton adminButton = new JButton("Admin");
        JButton userButton = new JButton("User");

        adminButton.addActionListener(e -> System.out.println("Admin Login"));
        userButton.addActionListener(e -> System.out.println("User Login"));

        panel.add(new JLabel("เลือกหน้าที่ของคุณ:"));
        panel.add(adminButton);
        panel.add(userButton);

        frame.setVisible(true);

        adminButton.addActionListener(e -> adminLogin(frame));
        userButton.addActionListener(e -> userLogin(frame));
    }

    private static void adminLogin(JFrame parent) {
        String username = JOptionPane.showInputDialog(parent, "ชื่อผู้ใช้แอดมิน:");
        String password = JOptionPane.showInputDialog(parent, "รหัสผ่าน:");

        if (admins.containsKey(username) && admins.get(username).equals(password)) {
            showAdminMenu();  // เข้าสู่เมนูแอดมิน
        } else {
            JOptionPane.showMessageDialog(parent, "ชื่อผู้ใช้หรือรหัสผ่านไม่ถูกต้อง!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void userLogin(JFrame parent) {
        String username = JOptionPane.showInputDialog(parent, "ชื่อผู้ใช้:");
        String room = JOptionPane.showInputDialog(parent, "รหัสห้อง:");
        String floor = JOptionPane.showInputDialog(parent, "ชั้น:");

        // ใช้ AccessValidator เพื่อตรวจสอบการเข้าถึง
        if (AccessValidator.validateUserAccess(username, room, floor)) {
            showMainMenuAfterAction(parent);
        } else {
            JOptionPane.showMessageDialog(parent, "ข้อมูลไม่ถูกต้อง!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void showAdminMenu() {
        String[] options = {"เพิ่ม-ลบผู้ใช้", "เพิ่ม-ลบแอดมิน", "แก้ไขข้อมูลบัตรผู้ใช้", "บันทึกข้อมูลการเข้าถึง", "โชว์ข้อมูลทั้งหมด", "ออกจากระบบ"};
        while (true) {
            int choice = JOptionPane.showOptionDialog(null, "เลือกตัวเลือก:", "Admin Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0 -> manageUsers();  // เรียกใช้เมธอดจัดการผู้ใช้
                case 1 -> manageAdmins();  // เรียกใช้เมธอดจัดการแอดมิน
                case 2 -> editUserInfo();  // แก้ไขข้อมูลบัตรผู้ใช้
                case 3 -> showAccessLogs();  // แสดงบันทึกการเข้าถึง
                case 4 -> showAllData();  // แสดงข้อมูลทั้งหมด
                case 5 -> {
                    return;  // ออกจากระบบ
                }
            }
        }
    }

    private static void showMainMenuAfterAction(JFrame parent) {
        int choice = JOptionPane.showConfirmDialog(parent, "ต้องการกลับไปที่หน้าหลัก?", "กลับไปหน้าหลัก", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            createMainMenu();  // กลับไปที่เมนูหลัก
            parent.dispose();  // ปิดหน้าต่างปัจจุบัน
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
}
