package com.github.zigcat.ormlite.models;

import com.github.zigcat.ormlite.controllers.SpecificCreateController;
import com.github.zigcat.ormlite.parameters.Creatable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "food")
public class Food implements Creatable {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String name;

    @DatabaseField
    private double calories;

    @DatabaseField
    private double proteins;

    @DatabaseField
    private double fats;

    @DatabaseField
    private double carbohydrates;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private User user;

    public static SpecificCreateController<Food> controller = new SpecificCreateController<>(Food.class);

    public Food() {
    }

    public Food(int id, String name, double calories, double proteins, double fats, double carbohydrates, User user) {
        this.id = id;
        this.name = name;
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
        this.user = user;
    }

    public Food(int id, String name, double calories, double proteins, double fats, double carbohydrates) {
        this.id = id;
        this.name = name;
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getProteins() {
        return proteins;
    }

    public void setProteins(double proteins) {
        this.proteins = proteins;
    }

    public double getFats() {
        return fats;
    }

    public void setFats(double fats) {
        this.fats = fats;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", calories=" + calories +
                '}';
    }
}
