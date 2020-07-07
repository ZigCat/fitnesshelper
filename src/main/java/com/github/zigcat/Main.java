package com.github.zigcat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.zigcat.jackson.deserializers.*;
import com.github.zigcat.jackson.serializers.*;
import com.github.zigcat.ormlite.models.*;
import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create();
        app.config.enableDevLogging();
        app.start(34567);
        //
        ObjectMapper om = new ObjectMapper();
        SimpleModule sm = new SimpleModule();
        sm.addSerializer(User.class, new UserSerializer());
        sm.addDeserializer(User.class, new UserDeserializer());
        sm.addSerializer(Food.class, new FoodSerializer());
        sm.addDeserializer(Food.class, new FoodDeserializer());
        sm.addSerializer(Exercise.class, new ExerciseSerializer());
        sm.addDeserializer(Exercise.class, new ExerciseDeserializer());
        sm.addSerializer(ExerciseUser.class, new ExerciseUserSerializer());
        sm.addDeserializer(ExerciseUser.class, new ExerciseUserDeserializer());
        sm.addSerializer(TrainingGraph.class, new TrainingGraphSerializer());
        sm.addDeserializer(TrainingGraph.class, new TrainingGraphDeserializer());
        sm.addSerializer(CalorieGraph.class, new CalorieGraphSerializer());
        sm.addDeserializer(CalorieGraph.class, new CalorieGraphDeserializer());
        om.registerModule(sm);
        //
        ObjectMapper omAdmin = new ObjectMapper();
        SimpleModule smAdmin = new SimpleModule();
        smAdmin.addSerializer(User.class, new UserAdminSerializer());
        omAdmin.registerModule(smAdmin);
        //
        app.get("user/", ctx -> User.controller.getAll(ctx, om, omAdmin));
        app.post("user/", ctx -> User.controller.create(ctx, om));
        app.patch("user/", ctx -> User.controller.update(ctx, om));
        app.delete("user/:id", ctx -> User.controller.delete(ctx));
        //
        app.get("food/", ctx -> Food.controller.getAll(ctx, om));
        app.post("food/", ctx -> Food.controller.create(ctx, om));
        app.patch("food/", ctx -> Food.controller.update(ctx, om));
        app.delete("food/:id", ctx -> Food.controller.delete(ctx));
        //
        app.get("exercise/", ctx -> Exercise.controller.getAll(ctx, om, omAdmin));
        app.post("exercise/", ctx -> Exercise.controller.create(ctx, om));
        app.patch("exercise/", ctx -> Exercise.controller.update(ctx, om));
        app.delete("exercise/:id", ctx -> Exercise.controller.delete(ctx));
        //
        app.get("exerciseUser/", ctx -> ExerciseUser.controller.getAll(ctx, om));
        app.post("exerciseUser/", ctx -> ExerciseUser.controller.create(ctx, om));
        app.patch("exerciseUser/", ctx -> ExerciseUser.controller.update(ctx, om));
        app.delete("exerciseUser/:id", ctx -> ExerciseUser.controller.delete(ctx));
        //
        app.get("trainingGraph/", ctx -> TrainingGraph.controller.getAll(ctx, om));
        app.post("trainingGraph/", ctx -> TrainingGraph.controller.create(ctx, om));
        app.patch("trainingGraph/", ctx -> TrainingGraph.controller.update(ctx, om));
        app.delete("trainingGraph/:id", ctx -> TrainingGraph.controller.delete(ctx));
        //
        app.get("calorieGraph/", ctx -> CalorieGraph.controller.getAll(ctx, om));
        app.post("calorieGraph/", ctx -> CalorieGraph.controller.create(ctx, om));
        app.patch("calorieGraph/", ctx -> CalorieGraph.controller.update(ctx, om));
        app.delete("calorieGraph/:id", ctx -> CalorieGraph.controller.delete(ctx));
    }
}