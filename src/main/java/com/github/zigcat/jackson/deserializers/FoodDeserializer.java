package com.github.zigcat.jackson.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.zigcat.ormlite.models.Food;

import java.io.IOException;

public class FoodDeserializer extends StdDeserializer<Food> {
    protected FoodDeserializer(Class<?> vc) {
        super(vc);
    }

    protected FoodDeserializer(JavaType valueType) {
        super(valueType);
    }

    protected FoodDeserializer(StdDeserializer<?> src) {
        super(src);
    }

    public FoodDeserializer(){
        super(Food.class);
    }

    @Override
    public Food deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        int id = node.get("id").asInt();
        String name = node.get("name").asText();
        double calories = node.get("calories").asDouble();
        double fats = node.get("fats").asDouble();
        double proteins = node.get("proteins").asDouble();
        double carbohydrates = node.get("carbohydrates").asDouble();
        return new Food(id, name, calories, proteins, fats, carbohydrates);
    }
}
