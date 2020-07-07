package com.github.zigcat.jackson.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.zigcat.exceptions.RedirectedException;
import com.github.zigcat.ormlite.models.CalorieGraph;
import com.github.zigcat.ormlite.models.User;

import java.io.IOException;
import java.sql.SQLException;
import java.time.DayOfWeek;

public class CalorieGraphDeserializer extends StdDeserializer<CalorieGraph> {
    protected CalorieGraphDeserializer(Class<?> vc) {
        super(vc);
    }

    protected CalorieGraphDeserializer(JavaType valueType) {
        super(valueType);
    }

    protected CalorieGraphDeserializer(StdDeserializer<?> src) {
        super(src);
    }

    public CalorieGraphDeserializer(){
        super(CalorieGraph.class);
    }

    @Override
    public CalorieGraph deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        int id = node.get("id").asInt();
        int userId = node.get("user").asInt();
        double calories = node.get("calories").asDouble();
        double fats = node.get("fats").asDouble();
        double proteins = node.get("proteins").asDouble();
        double carbohydrates = node.get("carbohydrates").asDouble();
        String dayOfWeek = node.get("dayOfWeek").asText();
        try {
            User user = User.controller.getService().getById(User.controller.getDao(), userId);
            return new CalorieGraph(id, user, calories, fats, proteins, carbohydrates, DayOfWeek.valueOf(dayOfWeek));
        } catch (SQLException e) {
            throw new RedirectedException("SQLException e");
        }
    }
}
