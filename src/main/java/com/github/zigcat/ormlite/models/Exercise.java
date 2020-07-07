package com.github.zigcat.ormlite.models;

import com.github.zigcat.ormlite.controllers.SpecificSearchController;
import com.github.zigcat.ormlite.parameters.FullModelable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "exercize")
public class Exercise implements FullModelable {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String name;

    @DatabaseField
    private String description;

    @DatabaseField
    private Muscles muscle;

    public static SpecificSearchController<Exercise> controller = new SpecificSearchController<>(Exercise.class);

    public Exercise() {
    }

    public Exercise(int id, String name, String description, Muscles muscle) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.muscle = muscle;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Muscles getMuscle() {
        return muscle;
    }

    public void setMuscle(Muscles muscle) {
        this.muscle = muscle;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", muscle='" + muscle + '\'' +
                '}';
    }
}