package com.github.zigcat.ormlite.models;

import com.github.zigcat.ormlite.controllers.Controller;
import com.github.zigcat.ormlite.parameters.Modelable;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.time.DayOfWeek;

@DatabaseTable(tableName = "calorie_graph")
public class CalorieGraph implements Modelable {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreignAutoRefresh = true, foreign = true)
    private User user;

    @DatabaseField
    private double calories;

    @DatabaseField
    private double fats;

    @DatabaseField
    private double proteins;

    @DatabaseField
    private double carbohydrates;

    @DatabaseField(dataType = DataType.ENUM_STRING)
    private DayOfWeek dayOfWeek;

    public static Controller<CalorieGraph> controller = new Controller<>(CalorieGraph.class);

    public CalorieGraph() {
    }

    public CalorieGraph(int id, User user, double calories, double fats, double proteins, double carbohydrates, DayOfWeek dayOfWeek) {
        this.id = id;
        this.user = user;
        this.calories = calories;
        this.fats = fats;
        this.proteins = proteins;
        this.carbohydrates = carbohydrates;
        this.dayOfWeek = dayOfWeek;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getFats() {
        return fats;
    }

    public void setFats(double fats) {
        this.fats = fats;
    }

    public double getProteins() {
        return proteins;
    }

    public void setProteins(double proteins) {
        this.proteins = proteins;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @Override
    public String toString() {
        return "CalorieGraph{" +
                "id=" + id +
                ", user=" + user.getId() +
                ", calories=" + calories +
                ", dayOfWeek=" + dayOfWeek.toString() +
                '}';
    }
}
