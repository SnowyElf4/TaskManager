package src.repositories;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Predicate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonRepository<T> {
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private List<T> items = new ArrayList<>();
    private String filePath;
    private Type listType = null;

    public JsonRepository(String filePath, Type listType) {
        this.filePath = filePath;
        this.listType = listType;
    }

    public void load() {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                File newFile = new File(filePath);
                if (newFile.createNewFile()) {
                    try (Writer writer = new FileWriter(filePath)) {
                        gson.toJson(items, writer);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            items = new ArrayList<>();
            return;
        }

        try (Reader reader = new FileReader(file)) {
            items = gson.fromJson(reader, listType);
            if (items == null) {
                items = new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            items = new ArrayList<>();
        }
    }

    public void save() {
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(items, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void add(T item) {
        boolean added = items.add(item);

        if (added) {
            save();
        }
    }

    public T find(Predicate<T> condition) {
        for (T item : items) {
            if (condition.test(item)) {
                return item;
            }
        }
        return null;
    }

    public void update(Predicate<T> condition, T updatedItem) {
        boolean changed = false;
        for (int i = 0; i < items.size(); i++) {
            T item = items.get(i);
            if (condition.test(item)) {
                items.set(i, updatedItem);
                changed = true;
            }
        }
        if (changed) {
            save();
        }
    }

    public void delete(Predicate<T> condition) {
        boolean removed = items.removeIf(condition);
        if (removed) {
            save();
        }
    }

    public List<T> getAll() { return new ArrayList<>(items); }
}
