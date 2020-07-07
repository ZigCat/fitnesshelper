package com.github.zigcat.ormlite.models;

import com.github.zigcat.ormlite.controllers.Controller;
import com.github.zigcat.ormlite.parameters.Modelable;
import com.github.zigcat.ormlite.services.Service;
import com.github.zigcat.ormlite.services.TrainingGraphService;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.time.DayOfWeek;

@DatabaseTable(tableName = "training_graph")
public class TrainingGraph implements Modelable {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private ExerciseUser exerciseUser;

    @DatabaseField(dataType = DataType.ENUM_STRING)
    private DayOfWeek dayOfWeek;

    public static Controller<TrainingGraph> controller = new Controller<>(TrainingGraph.class);
    public static TrainingGraphService service = new TrainingGraphService();

    public TrainingGraph() {
    }

    public TrainingGraph(int id, ExerciseUser exerciseUser, DayOfWeek dayOfWeek) {
        this.id = id;
        this.exerciseUser = exerciseUser;
        this.dayOfWeek = dayOfWeek;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ExerciseUser getExerciseUser() {
        return exerciseUser;
    }

    public void setExerciseUser(ExerciseUser exerciseUser) {
        this.exerciseUser = exerciseUser;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @Override
    public String toString() {
        return "TrainingGraph{" +
                "id=" + id +
                ", exerciseUser=" + exerciseUser.getId() +
                ", dayOfWeek='" + dayOfWeek.toString() + '\'' +
                '}';
    }
}
