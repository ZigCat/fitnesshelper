package com.github.zigcat.ormlite.models;

import com.github.zigcat.ormlite.controllers.Controller;
import com.github.zigcat.ormlite.parameters.Creatable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "exercize_user")
public class ExerciseUser implements Creatable {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Exercise exercise;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private User user;

    @DatabaseField
    private int laps;

    @DatabaseField
    private int counts;

    @DatabaseField
    private double heaviness;

    @DatabaseField
    private boolean isRunning;

    public static Controller<ExerciseUser> controller = new Controller<>(ExerciseUser.class);
    private String name;

    public ExerciseUser() {
    }

    public ExerciseUser(int id, Exercise exercise, User user, int laps, int counts, double heaviness) {
        this.id = id;
        this.exercise = exercise;
        this.user = user;
        this.laps = laps;
        this.counts = counts;
        this.heaviness = heaviness;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getLaps() {
        return laps;
    }

    public void setLaps(int laps) {
        this.laps = laps;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    public double getHeaviness() {
        return heaviness;
    }

    public void setHeaviness(double heaviness) {
        this.heaviness = heaviness;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        return "ExerciseUser{" +
                "id=" + id +
                ", exercise=" + exercise.getId() +
                ", user=" + user.getId() +
                ", laps=" + laps +
                ", counts=" + counts +
                ", heaviness=" + heaviness +
                '}';
    }
}
