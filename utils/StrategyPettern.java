package utils;

interface AccessStrategy {
    boolean validate(String floor);
}

class LowFloorAccessStrategy implements AccessStrategy {
    @Override
    public boolean validate(String floor) {
        return floor.equals("Low");
    }
}

class HighFloorAccessStrategy implements AccessStrategy {
    @Override
    public boolean validate(String floor) {
        return floor.equals("High");
    }
}
