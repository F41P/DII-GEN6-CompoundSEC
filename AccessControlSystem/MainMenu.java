package AccessControlSystem;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

import static AccessControlSystem.AccessControl.*;
import static AccessControlSystem.AuditLog.*;

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
            showAdminMenu();
        } else {
            JOptionPane.showMessageDialog(parent, "ชื่อผู้ใช้หรือรหัสผ่านไม่ถูกต้อง!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void userLogin(JFrame parent) {
        String username = JOptionPane.showInputDialog(parent, "ชื่อผู้ใช้:");
        String room = JOptionPane.showInputDialog(parent, "รหัสห้อง:");
        String floor = JOptionPane.showInputDialog(parent, "ชั้น:");

        if (users.containsKey(username) && users.get(username)[0].equals(room) && users.get(username)[1].equals(floor)) {
            String timestamp = LocalDateTime.now().format(formatter);
            String logEntry = timestamp + " - " + username + " เข้าถึงห้อง " + room + " ชั้น " + floor;
            accessLogs.add(logEntry);

            JOptionPane.showMessageDialog(parent, "เข้าถึงห้องสำเร็จ!\nเวลา: " + timestamp);
            showMainMenuAfterAction(parent);
        } else {
            JOptionPane.showMessageDialog(parent, "ข้อมูลไม่ถูกต้อง!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

