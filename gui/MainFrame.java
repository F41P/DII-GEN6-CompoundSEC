package gui;

import models.Admin;
import models.AuditLog;
import models.Card;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

import static models.Admin.admins;
import static models.Admin.users;

public class MainFrame {
    // หน้าจอหลักของระบบ โดยใช้ JFrame จาก Swing
    public static void createMainMenu() {
        JFrame frame = new JFrame("Access Control System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        JPanel panel = new JPanel(new GridLayout(3, 1));
        frame.add(panel);

        JButton adminButton = new JButton("Admin");
        JButton userButton = new JButton("User");

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

        if (Admin.isValidAdmin(username, password)) {
            showAdminMenu(parent);
        } else {
            JOptionPane.showMessageDialog(parent, "ชื่อผู้ใช้หรือรหัสผ่านไม่ถูกต้อง!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void userLogin(JFrame parent) {
        String username = JOptionPane.showInputDialog(parent, "ชื่อผู้ใช้:");
        Map<String, Card> users = Admin.getUsers();
        Card userCard = users.get(username);

        if (userCard != null) {
            // บันทึกการเข้าใช้งาน
            AuditLog.logUserAccess(username, userCard.getRoom(), userCard.getFloor());
            showUserMenu(parent, userCard);
        } else {
            JOptionPane.showMessageDialog(parent, "ไม่พบข้อมูลผู้ใช้", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void showAdminMenu(JFrame parent) {
        String[] options = {"เพิ่ม/ลบ ผู้ใช้", "เพิ่ม/ลบ แอดมิน", "แก้ไขผู้ใช้", "ดู Log", "ดูข้อมูลทั้งหมด", "ออกจากระบบ"};
        while (true) {
            int choice = JOptionPane.showOptionDialog(parent, "เลือกตัวเลือก:", "Admin Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0 -> manageUsers(parent);
                case 1 -> manageAdmins(parent);
                case 2 -> editUserInfo(parent);
                case 3 -> showAuditLogs();
                case 4 -> showAllData();
                case 5 -> {
                    return;// ออกจากระบบ
                }
            }
        }
    }

    private static void manageUsers(JFrame parent) {
        String[] options = {"เพิ่มผู้ใช้", "ลบผู้ใช้"};
        int choice = JOptionPane.showOptionDialog(parent, "เลือกตัวเลือก:", "Manage Users",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            String username = JOptionPane.showInputDialog(parent, "ชื่อผู้ใช้:");
            String room = JOptionPane.showInputDialog(parent, "รหัสห้อง:");
            String floor = JOptionPane.showInputDialog(parent, "ชั้น:");
            boolean isVIP = JOptionPane.showConfirmDialog(parent, "VIP?", "VIP", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
            Admin.addUser(username, new Card(username, room, floor, isVIP));
            JOptionPane.showMessageDialog(parent, "เพิ่มผู้ใช้ใหม่สำเร็จ!");
            AuditLog.logAdminAction("Admin", "Added user: " + username);  // บันทึกการกระทำแอดมิน
        } else if (choice == 1) {
            String username = JOptionPane.showInputDialog(parent, "ชื่อผู้ใช้ที่ต้องการลบ:");
            Admin.removeUser(username);
            JOptionPane.showMessageDialog(parent, "ลบผู้ใช้สำเร็จ!");
            AuditLog.logAdminAction("Admin", "Removed user: " + username);  // บันทึกการกระทำแอดมิน
        }
    }

    private static void manageAdmins(JFrame parent) {
        String[] options = {"เพิ่มแอดมิน", "ลบแอดมิน"};
        int choice = JOptionPane.showOptionDialog(parent, "เลือกตัวเลือก:", "Manage Admins",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            String adminName = JOptionPane.showInputDialog(parent, "ชื่อแอดมิน:");
            String password = JOptionPane.showInputDialog(parent, "รหัสผ่าน:");
            Admin.addAdmin(adminName, password);
            JOptionPane.showMessageDialog(parent, "เพิ่มแอดมินใหม่สำเร็จ!");
            AuditLog.logAdminAction("Admin", "Added admin: " + adminName);  // บันทึกการกระทำแอดมิน
        } else if (choice == 1) {
            String adminName = JOptionPane.showInputDialog(parent, "ชื่อแอดมินที่ต้องการลบ:");
            Admin.removeAdmin(adminName);
            JOptionPane.showMessageDialog(parent, "ลบแอดมินสำเร็จ!");
            AuditLog.logAdminAction("Admin", "Removed admin: " + adminName);  // บันทึกการกระทำแอดมิน
        }
    }

    private static void showAuditLogs() {
        JOptionPane.showMessageDialog(null, String.join("\n", AuditLog.getLogs()), "Audit Logs", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void showUserMenu(JFrame parent, Card userCard) {
        JOptionPane.showMessageDialog(parent,
                "Welcome, " + userCard.getCardID() + "!\n" +
                        "Room: " + userCard.getRoom() + "\n" +
                        "Floor: " + userCard.getFloor(),
                "User Menu",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private static void editUserInfo(JFrame parent) {
        String username = JOptionPane.showInputDialog(parent, "ชื่อผู้ใช้ที่ต้องการแก้ไข:");
        if (users.containsKey(username)) {
            Card card = users.get(username);
            String room = JOptionPane.showInputDialog(parent, "รหัสห้องใหม่:", card.getRoom());
            String floor = JOptionPane.showInputDialog(parent, "ชั้นใหม่:", card.getFloor());
            String vipStatus = JOptionPane.showInputDialog(parent, "VIP (true/false):");

            if (room != null && !room.isEmpty() && floor != null && !floor.isEmpty() && vipStatus != null) {
                boolean isVIP = Boolean.parseBoolean(vipStatus);
                card.setRoom(room);
                card.setFloor(floor);
                card.setVIP(isVIP);
                JOptionPane.showMessageDialog(parent, "แก้ไขข้อมูลผู้ใช้สำเร็จ!");
                AuditLog.logAdminAction("Admin", "Edited user: " + username);  // บันทึกการกระทำแอดมิน
            } else {
                JOptionPane.showMessageDialog(parent, "ข้อมูลไม่สมบูรณ์", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(parent, "ไม่พบผู้ใช้", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void showAllData() {
        StringBuilder sb = new StringBuilder("ข้อมูลผู้ใช้:\n");
        users.forEach((key, value) -> sb.append(key).append(" - ห้อง: ").append(value.getRoom()).append(" ชั้น: ").append(value.getFloor()).append(" VIP: ").append(value.isVIP()).append("\n"));
        sb.append("\nข้อมูลแอดมิน:\n");
        admins.forEach((key, value) -> sb.append(key).append("\n"));

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        JOptionPane.showMessageDialog(null, new JScrollPane(textArea), "ข้อมูลทั้งหมด", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        createMainMenu();
    }
}
