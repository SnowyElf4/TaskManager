package src.services;

import java.util.*;

import src.interfaces.Identifiable;

public class EntityService<T extends Identifiable> {
    private IdGenerator idGen = new IdGenerator();
    private List<T> items = new ArrayList<>();

    public void create(T item) {
        int id = idGen.generateId();

        while (true) {
            boolean uniq = true;
            for (T existingItem : items) {
                if (existingItem.getId() == id) {
                    id = idGen.generateId();
                    uniq = false;
                    break;
                }
            }
            if (uniq) {
                break;
            }
        }
        item.setId(id);
        items.add(item);
    }
}
