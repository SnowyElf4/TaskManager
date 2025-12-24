package TaskManager.service;

public class IdGenerator {
    public int generateId() {
        int min = 10000000; int max = 99999999;
        int randomId = min + (int)((max - min + 1) * Math.random());
        return randomId;
    }
}
