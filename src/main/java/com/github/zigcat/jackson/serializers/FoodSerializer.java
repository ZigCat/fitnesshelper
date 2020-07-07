package com.github.zigcat.jackson.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.zigcat.ormlite.models.Food;

import java.io.IOException;

public class FoodSerializer extends StdSerializer<Food> {
    protected FoodSerializer(Class<Food> t) {
        super(t);
    }

    protected FoodSerializer(JavaType type) {
        super(type);
    }

    protected FoodSerializer(Class<?> t, boolean dummy) {
        super(t, dummy);
    }

    protected FoodSerializer(StdSerializer<?> src) {
        super(src);
    }

    public FoodSerializer(){
        super(Food.class);
    }

    @Override
    public void serialize(Food food, JsonGenerator json, SerializerProvider serializerProvider) throws IOException {
        json.writeStartObject();
        json.writeNumberField("id", food.getId());
        json.writeStringField("name", food.getName());
        json.writeNumberField("calories", food.getCalories());
        json.writeNumberField("proteins", food.getProteins());
        json.writeNumberField("fats", food.getFats());
        json.writeNumberField("carbohydrates", food.getCarbohydrates());
        json.writeObjectField("user", food.getUser());
        json.writeEndObject();
    }
}
