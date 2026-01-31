package domain.user;

import interfaces.Identifiable;

public class User implements Identifiable {
    private String name;
    private int id;

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int savedId) {
        this.id = savedId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}