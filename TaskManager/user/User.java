package TaskManager.user;

public class User {
    private String name;
    private int id;

    public void setId(int savedId) {
        this.id = savedId;
    }

    public int getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}

