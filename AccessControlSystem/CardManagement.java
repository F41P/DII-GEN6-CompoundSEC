package AccessControlSystem;
import javax.swing.*;
import static AccessControlSystem.AccessControl.admins;
import static AccessControlSystem.AccessControl.users;

public class CardManagement {

    public static void manageUsers() {
        String[] options = {"เพิ่มผู้ใช้", "ลบผู้ใช้"};
        int choice = JOptionPane.showOptionDialog(null, "เลือกตัวเลือก:", "Manage Users",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            String username = JOptionPane.showInputDialog("ชื่อผู้ใช้:");
            String room = JOptionPane.showInputDialog("รหัสห้อง:");
            String floor = JOptionPane.showInputDialog("ชั้น:");
            users.put(username, new String[]{room, floor});
            JOptionPane.showMessageDialog(null, "เพิ่มผู้ใช้สำเร็จ");
        } else if (choice == 1) {
            String username = JOptionPane.showInputDialog("ชื่อผู้ใช้ที่ต้องการลบ:");
            if (users.containsKey(username)) {
                users.remove(username);
                JOptionPane.showMessageDialog(null, "ลบผู้ใช้สำเร็จ");
            } else {
                JOptionPane.showMessageDialog(null, "ไม่พบผู้ใช้", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void manageAdmins() {
        String[] options = {"เพิ่มแอดมิน", "ลบแอดมิน"};
        int choice = JOptionPane.showOptionDialog(null, "เลือกตัวเลือก:", "Manage Admins",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            String username = JOptionPane.showInputDialog("ชื่อแอดมิน:");
            String password = JOptionPane.showInputDialog("รหัสผ่าน:");
            admins.put(username, password);
            JOptionPane.showMessageDialog(null, "เพิ่มแอดมินสำเร็จ");
        } else if (choice == 1) {
            String username = JOptionPane.showInputDialog("ชื่อแอดมินที่ต้องการลบ:");
            if (admins.containsKey(username)) {
                admins.remove(username);
                JOptionPane.showMessageDialog(null, "ลบแอดมินสำเร็จ");
            } else {
                JOptionPane.showMessageDialog(null, "ไม่พบแอดมิน", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
