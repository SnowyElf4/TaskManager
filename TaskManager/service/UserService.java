package TaskManager.service;
import TaskManager.user.*;

public class UserService {
    private IdGenerator idGen = new IdGenerator();

    public User createUser(String name) {
        int id = idGen.generateId();
        User user = new User();
        user.setId(id);
        user.setName(name);
        return user;
    }
}
