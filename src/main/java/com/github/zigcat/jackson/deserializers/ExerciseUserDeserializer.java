package com.github.zigcat.jackson.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.zigcat.exceptions.RedirectedException;
import com.github.zigcat.ormlite.models.Exercise;
import com.github.zigcat.ormlite.models.ExerciseUser;
import com.github.zigcat.ormlite.models.User;

import java.io.IOException;
import java.sql.SQLException;

public class ExerciseUserDeserializer extends StdDeserializer<ExerciseUser> {
    protected ExerciseUserDeserializer(Class<?> vc) {
        super(vc);
    }

    protected ExerciseUserDeserializer(JavaType valueType) {
        super(valueType);
    }

    protected ExerciseUserDeserializer(StdDeserializer<?> src) {
        super(src);
    }

    public ExerciseUserDeserializer(){
        super(ExerciseUser.class);
    }

    @Override
    public ExerciseUser deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        int id = node.get("id").asInt();
        int exerciseId = node.get("exercise").asInt();
        int userId = node.get("user").asInt();
        int laps = node.get("laps").asInt();
        int counts = node.get("counts").asInt();
        double heaviness = node.get("heaviness").asDouble();
        try {
            User user = User.controller.getService().getById(User.controller.getDao(), userId);
            Exercise exercise = Exercise.controller.getService().getById(Exercise.controller.getDao(), exerciseId);
            return new ExerciseUser(id, exercise, user, laps, counts, heaviness);
        } catch (SQLException e) {
            throw new RedirectedException("SQLException e");
        }
    }
}
