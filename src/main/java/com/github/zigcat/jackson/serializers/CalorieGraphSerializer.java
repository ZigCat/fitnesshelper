package com.github.zigcat.jackson.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.zigcat.ormlite.models.CalorieGraph;

import java.io.IOException;

public class CalorieGraphSerializer extends StdSerializer<CalorieGraph> {
    protected CalorieGraphSerializer(Class<CalorieGraph> t) {
        super(t);
    }

    protected CalorieGraphSerializer(JavaType type) {
        super(type);
    }

    protected CalorieGraphSerializer(Class<?> t, boolean dummy) {
        super(t, dummy);
    }

    protected CalorieGraphSerializer(StdSerializer<?> src) {
        super(src);
    }

    public CalorieGraphSerializer(){
        super(CalorieGraph.class);
    }

    @Override
    public void serialize(CalorieGraph calorieGraph, JsonGenerator json, SerializerProvider serializerProvider) throws IOException {
        json.writeStartObject();
        json.writeNumberField("id", calorieGraph.getId());
        json.writeObjectField("user", calorieGraph.getUser());
        json.writeNumberField("calories", calorieGraph.getCalories());
        json.writeNumberField("fats", calorieGraph.getFats());
        json.writeNumberField("proteins", calorieGraph.getProteins());
        json.writeNumberField("carbohydrates", calorieGraph.getCarbohydrates());
        json.writeStringField("dayOfWeek", calorieGraph.getDayOfWeek().toString());
        json.writeEndObject();
    }
}
