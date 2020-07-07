package com.github.zigcat.ormlite.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.zigcat.exceptions.RedirectedException;
import com.github.zigcat.ormlite.models.*;
import com.github.zigcat.ormlite.services.Security;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProgramCreator {
    private static Logger l = LoggerFactory.getLogger(ProgramCreator.class);

    public ProgramCreator(){

    }

    public void createProgram(Context ctx, ObjectMapper omAdmin, ObjectMapper om){
        l.info("@CREATING TRAINING PROGRAM@");
        String login = ctx.basicAuthCredentials().getUsername();
        String password = ctx.basicAuthCredentials().getPassword();
        Map map = ctx.queryParamMap();
        int perWeek;
        double running = 1.0, gaining = 1.0;
        boolean program;
        int exercises[] = new int[9];
        ArrayList<TrainingGraph> graph = new ArrayList<>();
        for(int i=0;i<9;i++){
            exercises[i] = 0;
        }
        try {
            List<Exercise> exerciseList = Exercise.controller.getDao().queryForAll();
            User user = Security.authorize(login, password);
            if(map.containsKey("perWeek")){
                perWeek = Integer.parseInt(ctx.queryParam("perWeek"));
            } else {
                throw new RedirectedException("query param");
            }
            program = TrainingGraph.service.isProgramExists(user);
            switch (user.getTrainingType()){
                case MASSGAIN:
                    gaining = 1.5;
                    break;
                case MASSLOSS:
                    running = 1.5;
                    break;
            }
            switch (perWeek){
                case 1:
                    for(int i=0;i<7;i++){
                        for(Exercise e: exerciseList){
                            if(i == 1 || i == 3 || i == 5 || i == 6){
                                break;
                            }
                            switch (i){
                                case 0:
                                    if((e.getMuscle().equals(Muscles.BICEPS) && exercises[0] < 4) && !program){
                                        graph.add(new TrainingGraph(1,
                                                new ExerciseUser(1, e, user, (int)(4/running*gaining), (int)(12/running*gaining), user.getWorkingWeight()*gaining/running),
                                                DayOfWeek.MONDAY));
                                        exercises[0] ++;
                                    } else if((e.getMuscle().equals(Muscles.CHEST) && exercises[6] < 4) && !program){
                                        if(e.getName().equals("жим лежа")){
                                            graph.add(new TrainingGraph(1,
                                                    new ExerciseUser(1, e, user, (int)(5/running*gaining), (int)(7/running*gaining), user.getWorkingWeight()*4*gaining/running),
                                                    DayOfWeek.MONDAY));
                                        } else {
                                            graph.add(new TrainingGraph(1,
                                                    new ExerciseUser(1, e, user, (int)(4/running*gaining), (int)(12/running*gaining), user.getWorkingWeight()*gaining/running),
                                                    DayOfWeek.MONDAY));
                                        }
                                        exercises[6] ++;
                                    } else if((e.getMuscle().equals(Muscles.PRESS) && exercises[7] < 2) && !program){
                                        //here working weight doesn't taken into account
                                        graph.add(new TrainingGraph(1,
                                                new ExerciseUser(1, e, user, 4, 15, user.getWorkingWeight()),
                                                DayOfWeek.MONDAY));
                                        exercises[7] ++;
                                    } else if((e.getMuscle().equals(Muscles.CARDIO) && exercises[8] < 1) && !program){
                                        //here working weight means minimal time to run
                                        graph.add(new TrainingGraph(1,
                                                new ExerciseUser(1, e, user, 1, 1, user.getWorkingWeight()*running/gaining),
                                                DayOfWeek.MONDAY));
                                        exercises[8] ++;
                                    }
                                    break;
                                case 2:
                                    if((e.getMuscle().equals(Muscles.TRICEPS) && exercises[1] < 4) && !program){
                                        graph.add(new TrainingGraph(1,
                                                new ExerciseUser(1, e, user, (int)(4/running*gaining), (int)(12/running*gaining), user.getWorkingWeight()*gaining/running),
                                                DayOfWeek.WEDNESDAY));
                                        exercises[1] ++;
                                    } else if((e.getMuscle().equals(Muscles.BACK) && exercises[2] < 4) && !program){
                                        graph.add(new TrainingGraph(1,
                                                new ExerciseUser(1, e, user, (int)(4/running*gaining), (int)(12/running*gaining), user.getWorkingWeight()*gaining/running),
                                                DayOfWeek.WEDNESDAY));
                                        exercises[2] ++;
                                    } else if((e.getMuscle().equals(Muscles.PRESS) && exercises[7] < 2) && !program){
                                        //here working weight doesn't taken into account
                                        graph.add(new TrainingGraph(1,
                                                new ExerciseUser(1, e, user, 4, 15, user.getWorkingWeight()),
                                                DayOfWeek.WEDNESDAY));
                                        exercises[7] ++;
                                    } else if((e.getMuscle().equals(Muscles.CARDIO) && exercises[8] < 1) && !program){
                                        //here working weight means minimal time to run
                                        graph.add(new TrainingGraph(1,
                                                new ExerciseUser(1, e, user, 1, 1, user.getWorkingWeight()*running/gaining),
                                                DayOfWeek.WEDNESDAY));
                                        exercises[8] ++;
                                    }
                                    break;
                                case 4:
                                    if((e.getMuscle().equals(Muscles.SHOULDERS) && exercises[5] < 4) && !program){
                                        graph.add(new TrainingGraph(1,
                                                new ExerciseUser(1, e, user, (int)(4/running*gaining), (int)(12/running*gaining), user.getWorkingWeight()*gaining/running),
                                                DayOfWeek.FRIDAY));
                                        exercises[5] ++;
                                    } else if((e.getMuscle().equals(Muscles.LEGS) && exercises[4] < 4) && !program){
                                        graph.add(new TrainingGraph(1,
                                                new ExerciseUser(1, e, user, (int)(4/running*gaining), (int)(12/running*gaining), user.getWorkingWeight()*gaining/running),
                                                DayOfWeek.FRIDAY));
                                        exercises[4] ++;
                                    } else if((e.getMuscle().equals(Muscles.PRESS) && exercises[7] < 2) && !program){
                                        //here working weight doesn't taken into account
                                        graph.add(new TrainingGraph(1,
                                                new ExerciseUser(1, e, user, 4, 15, user.getWorkingWeight()),
                                                DayOfWeek.FRIDAY));
                                        exercises[7] ++;
                                    } else if((e.getMuscle().equals(Muscles.LOINS) && exercises[3] < 2) && !program){
                                        //here working weight doesn't taken into account
                                        graph.add(new TrainingGraph(1,
                                                new ExerciseUser(1, e, user, 4, 15, user.getWorkingWeight()),
                                                DayOfWeek.FRIDAY));
                                        exercises[3] ++;
                                    }
                                    break;
                            }
                        }
                    }
                    break;
                case 2:
                    for(int i=0;i<7;i++){
                        for(Exercise e: exerciseList){
                            if(i == 1 || i == 3 || i == 6){
                                break;
                            }
                            switch(i){
                                case 0:
                                    if((e.getMuscle().equals(Muscles.BICEPS) && exercises[0] < 4) && !program){
                                        graph.add(new TrainingGraph(1,
                                                new ExerciseUser(1, e, user, (int)(4/running*gaining), (int)(12/running*gaining), user.getWorkingWeight()*gaining/running),
                                                DayOfWeek.MONDAY));
                                        exercises[0] ++;
                                    } else if((e.getMuscle().equals(Muscles.CHEST) && exercises[6] < 4) && !program){
                                        if(e.getName().equals("жим лежа")){
                                            graph.add(new TrainingGraph(1,
                                                    new ExerciseUser(1, e, user, (int)(5/running*gaining), (int)(7/running*gaining), user.getWorkingWeight()*4*gaining/running),
                                                    DayOfWeek.MONDAY));
                                        } else {
                                            graph.add(new TrainingGraph(1,
                                                    new ExerciseUser(1, e, user, (int)(4/running*gaining), (int)(12/running*gaining), user.getWorkingWeight()*gaining/running),
                                                    DayOfWeek.MONDAY));
                                        }
                                        exercises[6] ++;
                                    } else if((e.getMuscle().equals(Muscles.PRESS) && exercises[7] < 1) && !program){
                                        //here working weight doesn't taken into account
                                        graph.add(new TrainingGraph(1,
                                                new ExerciseUser(1, e, user, 4, 15, user.getWorkingWeight()),
                                                DayOfWeek.MONDAY));
                                        exercises[7] ++;
                                    }
                                    break;
                                case 2:
                                    if((e.getMuscle().equals(Muscles.SHOULDERS) && exercises[5] < 4) && !program){
                                        graph.add(new TrainingGraph(1,
                                                new ExerciseUser(1, e, user, (int)(4/running*gaining), (int)(12/running*gaining), user.getWorkingWeight()*gaining/running),
                                                DayOfWeek.WEDNESDAY));
                                        exercises[5] ++;
                                    } else if((e.getMuscle().equals(Muscles.LEGS) && exercises[4] < 4) && !program){
                                        graph.add(new TrainingGraph(1,
                                                new ExerciseUser(1, e, user, (int)(4/running*gaining), (int)(12/running*gaining), user.getWorkingWeight()*gaining/running),
                                                DayOfWeek.WEDNESDAY));
                                        exercises[4] ++;
                                    } else if((e.getMuscle().equals(Muscles.PRESS) && exercises[7] < 1) && !program){
                                        //here working weight doesn't taken into account
                                        graph.add(new TrainingGraph(1,
                                                new ExerciseUser(1, e, user, 4, 15, user.getWorkingWeight()),
                                                DayOfWeek.WEDNESDAY));
                                        exercises[7] ++;
                                    }
                                    break;
                                case 4:
                                    if((e.getMuscle().equals(Muscles.TRICEPS) && exercises[1] < 4) && !program){
                                        graph.add(new TrainingGraph(1,
                                                new ExerciseUser(1, e, user, (int)(4/running*gaining), (int)(12/running*gaining), user.getWorkingWeight()*gaining/running),
                                                DayOfWeek.FRIDAY));
                                        exercises[1] ++;
                                    } else if((e.getMuscle().equals(Muscles.BACK) && exercises[2] < 4) && !program){
                                        graph.add(new TrainingGraph(1,
                                                new ExerciseUser(1, e, user, (int)(4/running*gaining), (int)(12/running*gaining), user.getWorkingWeight()*gaining/running),
                                                DayOfWeek.FRIDAY));
                                        exercises[2] ++;
                                    } else if((e.getMuscle().equals(Muscles.PRESS) && exercises[7] < 1) && !program){
                                        //here working weight doesn't taken into account
                                        graph.add(new TrainingGraph(1,
                                                new ExerciseUser(1, e, user, 4, 15, user.getWorkingWeight()),
                                                DayOfWeek.FRIDAY));
                                        exercises[7] ++;
                                    }
                                    break;
                                case 5:
                                    if((e.getMuscle().equals(Muscles.LOINS) && exercises[3] < 2) && !program){
                                        //here working weight doesn't taken into account
                                        graph.add(new TrainingGraph(1,
                                                new ExerciseUser(1, e, user, 4, 15, user.getWorkingWeight()),
                                                DayOfWeek.SATURDAY));
                                        exercises[3] ++;
                                    } else if((e.getMuscle().equals(Muscles.CARDIO) && exercises[8] < 2) && !program){
                                        //here working weight means minimal time to run
                                        graph.add(new TrainingGraph(1,
                                                new ExerciseUser(1, e, user, 1, 1, user.getWorkingWeight()*running/gaining),
                                                DayOfWeek.SATURDAY));
                                        exercises[8] ++;
                                    } else if((e.getMuscle().equals(Muscles.PRESS) && exercises[7] < 4) && !program){
                                        //here working weight doesn't taken into account
                                        graph.add(new TrainingGraph(1,
                                                new ExerciseUser(1, e, user, 4, 15, user.getWorkingWeight()),
                                                DayOfWeek.SATURDAY));
                                        exercises[7] ++;
                                    }
                                    break;
                            }
                        }
                    }
                    break;
                case 3:
                    for(int i=0;i<7;i++){
                        for(Exercise e: exerciseList){
                            if(i == 6){
                                break;
                            }
                            switch(i){
                                case 0:
                                    if((e.getMuscle().equals(Muscles.BICEPS) && exercises[0] < 4) && !program){
                                        graph.add(new TrainingGraph(1,
                                                new ExerciseUser(1, e, user, (int)(4/running*gaining), (int)(12/running*gaining), user.getWorkingWeight()*gaining/running),
                                                DayOfWeek.MONDAY));
                                        exercises[0] ++;
                                    } else if((e.getMuscle().equals(Muscles.PRESS) && exercises[7] < 2) && !program){
                                        //here working weight doesn't taken into account
                                        graph.add(new TrainingGraph(1,
                                                new ExerciseUser(1, e, user, 4, 15, user.getWorkingWeight()),
                                                DayOfWeek.MONDAY));
                                        exercises[7] ++;
                                    } else if((e.getMuscle().equals(Muscles.CARDIO) && exercises[8] < 1) && !program){
                                        //here working weight means minimal time to run
                                        graph.add(new TrainingGraph(1,
                                                new ExerciseUser(1, e, user, 1, 1, user.getWorkingWeight()*running/gaining),
                                                DayOfWeek.MONDAY));
                                        exercises[8] ++;
                                    }
                                    break;
                                case 1:
                                    if((e.getMuscle().equals(Muscles.CHEST) && exercises[6] < 4) && !program){
                                        if(e.getName().equals("жим лежа")){
                                            graph.add(new TrainingGraph(1,
                                                    new ExerciseUser(1, e, user, (int)(5/running*gaining), (int)(7/running*gaining), user.getWorkingWeight()*4*gaining/running),
                                                    DayOfWeek.TUESDAY));
                                        } else {
                                            graph.add(new TrainingGraph(1,
                                                    new ExerciseUser(1, e, user, (int)(4/running*gaining), (int)(12/running*gaining), user.getWorkingWeight()*gaining/running),
                                                    DayOfWeek.TUESDAY));
                                        }
                                        exercises[6] ++;
                                    } else if((e.getMuscle().equals(Muscles.LOINS) && exercises[3] < 2) && !program){
                                        //here working weight doesn't taken into account
                                        graph.add(new TrainingGraph(1,
                                                new ExerciseUser(1, e, user, 4, 15, user.getWorkingWeight()),
                                                DayOfWeek.TUESDAY));
                                        exercises[3] ++;
                                    } else if((e.getMuscle().equals(Muscles.CARDIO) && exercises[8] < 1) && !program){
                                        //here working weight means minimal time to run
                                        graph.add(new TrainingGraph(1,
                                                new ExerciseUser(1, e, user, 1, 1, user.getWorkingWeight()*running/gaining),
                                                DayOfWeek.TUESDAY));
                                        exercises[8] ++;
                                    }
                                    break;
                                case 2:
                                    if((e.getMuscle().equals(Muscles.TRICEPS) && exercises[1] < 4) && !program){
                                        graph.add(new TrainingGraph(1,
                                                new ExerciseUser(1, e, user, (int)(4/running*gaining), (int)(12/running*gaining), user.getWorkingWeight()*gaining/running),
                                                DayOfWeek.WEDNESDAY));
                                        exercises[1] ++;
                                    } else if((e.getMuscle().equals(Muscles.PRESS) && exercises[7] < 2) && !program){
                                        //here working weight doesn't taken into account
                                        graph.add(new TrainingGraph(1,
                                                new ExerciseUser(1, e, user, 4, 15, user.getWorkingWeight()),
                                                DayOfWeek.WEDNESDAY));
                                        exercises[7] ++;
                                    } else if((e.getMuscle().equals(Muscles.CARDIO) && exercises[8] < 1) && !program){
                                        //here working weight means minimal time to run
                                        graph.add(new TrainingGraph(1,
                                                new ExerciseUser(1, e, user, 1, 1, user.getWorkingWeight()*running/gaining),
                                                DayOfWeek.WEDNESDAY));
                                        exercises[8] ++;
                                    }
                                    break;
                                case 3:
                                    if((e.getMuscle().equals(Muscles.BACK) && exercises[2] < 4) && !program){
                                        graph.add(new TrainingGraph(1,
                                                new ExerciseUser(1, e, user, (int)(4/running*gaining), (int)(12/running*gaining), user.getWorkingWeight()*gaining/running),
                                                DayOfWeek.THURSDAY));
                                        exercises[2] ++;
                                    } else if((e.getMuscle().equals(Muscles.LOINS) && exercises[3] < 2) && !program){
                                        //here working weight doesn't taken into account
                                        graph.add(new TrainingGraph(1,
                                                new ExerciseUser(1, e, user, 4, 15, user.getWorkingWeight()),
                                                DayOfWeek.THURSDAY));
                                        exercises[3] ++;
                                    } else if((e.getMuscle().equals(Muscles.CARDIO) && exercises[8] < 1) && !program){
                                        //here working weight means minimal time to run
                                        graph.add(new TrainingGraph(1,
                                                new ExerciseUser(1, e, user, 1, 1, user.getWorkingWeight()*running/gaining),
                                                DayOfWeek.THURSDAY));
                                        exercises[8] ++;
                                    }
                                    break;
                                case 4:
                                    if((e.getMuscle().equals(Muscles.SHOULDERS) && exercises[5] < 4) && !program){
                                        graph.add(new TrainingGraph(1,
                                                new ExerciseUser(1, e, user, (int)(4/running*gaining), (int)(12/running*gaining), user.getWorkingWeight()*gaining/running),
                                                DayOfWeek.FRIDAY));
                                        exercises[5] ++;
                                    } else if((e.getMuscle().equals(Muscles.PRESS) && exercises[7] < 2) && !program){
                                        //here working weight doesn't taken into account
                                        graph.add(new TrainingGraph(1,
                                                new ExerciseUser(1, e, user, 4, 15, user.getWorkingWeight()),
                                                DayOfWeek.FRIDAY));
                                        exercises[7] ++;
                                    } else if((e.getMuscle().equals(Muscles.CARDIO) && exercises[8] < 1) && !program){
                                        //here working weight means minimal time to run
                                        graph.add(new TrainingGraph(1,
                                                new ExerciseUser(1, e, user, 1, 1, user.getWorkingWeight()*running/gaining),
                                                DayOfWeek.FRIDAY));
                                        exercises[8] ++;
                                    }
                                    break;
                                case 5:
                                    if((e.getMuscle().equals(Muscles.LEGS) && exercises[4] < 4) && !program){
                                        graph.add(new TrainingGraph(1,
                                                new ExerciseUser(1, e, user, (int)(4/running*gaining), (int)(12/running*gaining), user.getWorkingWeight()*gaining/running),
                                                DayOfWeek.SATURDAY));
                                        exercises[4] ++;
                                    } else if((e.getMuscle().equals(Muscles.LOINS) && exercises[3] < 2) && !program){
                                        //here working weight doesn't taken into account
                                        graph.add(new TrainingGraph(1,
                                                new ExerciseUser(1, e, user, 4, 15, user.getWorkingWeight()),
                                                DayOfWeek.SATURDAY));
                                        exercises[3] ++;
                                    } else if((e.getMuscle().equals(Muscles.CARDIO) && exercises[8] < 1) && !program){
                                        //here working weight means minimal time to run
                                        graph.add(new TrainingGraph(1,
                                                new ExerciseUser(1, e, user, 1, 1, user.getWorkingWeight()*running/gaining),
                                                DayOfWeek.SATURDAY));
                                        exercises[8] ++;
                                    }
                                    break;
                            }
                        }
                    }
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            ctx.status(500);
            ctx.result(Security.serverErrorMessage);
            l.warn(Security.serverErrorMessage);
        } catch (RedirectedException e){
            ctx.status(400);
            ctx.result("Missing query param(400)");
            l.info(Security.queryParamMessage);
        }
        l.info("@QUERY DONE@");
    }
}
