package com.github.zigcat.jackson.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.zigcat.exceptions.RedirectedException;
import com.github.zigcat.ormlite.models.ExerciseUser;
import com.github.zigcat.ormlite.models.TrainingGraph;

import java.io.IOException;
import java.sql.SQLException;
import java.time.DayOfWeek;

public class TrainingGraphDeserializer extends StdDeserializer<TrainingGraph> {
    protected TrainingGraphDeserializer(Class<?> vc) {
        super(vc);
    }

    protected TrainingGraphDeserializer(JavaType valueType) {
        super(valueType);
    }

    protected TrainingGraphDeserializer(StdDeserializer<?> src) {
        super(src);
    }

    public TrainingGraphDeserializer(){
        super(TrainingGraph.class);
    }

    @Override
    public TrainingGraph deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        int id = node.get("id").asInt();
        int euId = node.get("exerciseUser").asInt();
        try {
            ExerciseUser eu = ExerciseUser.controller.getService().getById(ExerciseUser.controller.getDao(), euId);
            String dayOfWeek = node.get("dayOfWeek").asText();
            return new TrainingGraph(id, eu, DayOfWeek.valueOf(dayOfWeek));
        } catch (SQLException e) {
            throw new RedirectedException("SQLException");
        }
    }
}
