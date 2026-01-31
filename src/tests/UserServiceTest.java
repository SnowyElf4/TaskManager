package tests;

import appconfig.AppConfig;
import domain.user.User;
import repositories.UserRepository;
import services.UserService;

import java.io.File;

public class UserServiceTest {

    public static void main(String[] args) {
        // Clear Files before tests
        clearUsersFile();

        // tests
        testCreateUserWhenNameExists();
        testCreateUserWhenNameNotExists();
        testGetUsers();
        System.out.println("All tests done");

        clearUsersFile();
    }

    private static void testCreateUserWhenNameExists() {
        UserRepository userRepository = new UserRepository();
        UserService service = new UserService(userRepository);

        User user = new User();
        user.setId(1);
        user.setName("Alice");
        userRepository.addUser(user);

        User result = service.createUser("Alice");

        if (result != null) {
            throw new AssertionError("Expected null when user exists");
        }
    }

    private static void testCreateUserWhenNameNotExists() {
        UserRepository repo = new UserRepository();
        UserService service = new UserService(repo);

        User user = service.createUser("Bob");

        if (user == null || !"Bob".equals(user.getName())) {
            throw new AssertionError("User not created correctly");
        }
    }

    private static void testGetUsers() {
        UserRepository repo = new UserRepository();
        UserService service = new UserService(repo);

        User user1 = new User();
        user1.setId(1);
        user1.setName("Alice");
        repo.addUser(user1);

        User user2 = new User();
        user2.setId(2);
        user2.setName("Bob");
        repo.addUser(user2);

        if (repo.getUsers().size() != 4) {
            throw new AssertionError("Repository returned wrong size");
        }

        if (service.getUsers().size() != 4) {
            throw new AssertionError("UserService.getUsers returned wrong size");
        }
    }

    private static void clearUsersFile() {
        AppConfig appConfig = new AppConfig();
        File usersFile = new File(appConfig.getUsersFilePath());
        if (usersFile.exists())
            usersFile.delete();
    }
}
