package com.github.zigcat.ormlite.services;

import com.github.zigcat.ormlite.models.TrainingGraph;
import com.github.zigcat.ormlite.models.User;

import java.sql.SQLException;
import java.util.List;

public class TrainingGraphService extends Service<TrainingGraph> {
    public TrainingGraphService(){}

    public boolean isProgramExists(User user) throws SQLException {
        List<TrainingGraph> trainingGraphs = TrainingGraph.controller.getDao().queryForAll();
        for(TrainingGraph t: trainingGraphs){
            if(t.getExerciseUser().getUser().getId() == user.getId()){
                return true;
            }
        }
        return false;
    }
}
