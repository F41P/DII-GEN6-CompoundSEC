package AccessControlSystem;

abstract class AccessLevel {
    abstract boolean hasAccess(String room, String floor);
}

class LowFloorAccess extends AccessLevel {
    @Override
    boolean hasAccess(String room, String floor) {
        return floor.startsWith("L");  // เฉพาะชั้น L
    }
}

class MediumFloorAccess extends AccessLevel {
    @Override
    boolean hasAccess(String room, String floor) {
        return floor.startsWith("M");  // เฉพาะชั้น M
    }
}

class HighFloorAccess extends AccessLevel {
    @Override
    boolean hasAccess(String room, String floor) {
        return floor.startsWith("H");  // เฉพาะชั้น H
    }
}
