// AccessControl.java
package controllers;

public class AccessControl {
    private String userRole;
    private String userFloor;
    private String userRoom;
    private boolean hasAccess;

    public AccessControl(String userRole, String userFloor, String userRoom) {
        this.userRole = userRole;
        this.userFloor = userFloor;
        this.userRoom = userRoom;
        this.hasAccess = false;
    }

    // Method to check if the user has access
    public boolean checkAccess(String floor) {
        if (userRole.equals("Admin")) {
            return true;
        }
        // Only grant access if the user is assigned to the correct floor
        return userFloor.equals(floor);
    }

    // Getter and Setter
    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserFloor() {
        return userFloor;
    }

    public void setUserFloor(String userFloor) {
        this.userFloor = userFloor;
    }

    public String getUserRoom() {
        return userRoom;
    }

    public void setUserRoom(String userRoom) {
        this.userRoom = userRoom;
    }

    public boolean isHasAccess() {
        return hasAccess;
    }

    public void setHasAccess(boolean hasAccess) {
        this.hasAccess = hasAccess;
    }
}
