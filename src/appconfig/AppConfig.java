package src.appconfig;

import java.io.File;

public class AppConfig {
    private final String USERS_FILE = "data/users.json";
    private final String TASKS_FILE = "data/tasks.json";

    public AppConfig() {
        File dataDir = new File("data");
        if (!dataDir.exists())
            dataDir.mkdir();
    }

    public String getUsersFilePath() { return USERS_FILE; }
    public String getTasksFilePath() { return TASKS_FILE; }
}