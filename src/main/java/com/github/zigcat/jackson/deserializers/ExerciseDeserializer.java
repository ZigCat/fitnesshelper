package com.github.zigcat.jackson.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.zigcat.ormlite.models.Exercise;

import java.io.IOException;

public class ExerciseDeserializer extends StdDeserializer<Exercise> {
    protected ExerciseDeserializer(Class<?> vc) {
        super(vc);
    }

    protected ExerciseDeserializer(JavaType valueType) {
        super(valueType);
    }

    protected ExerciseDeserializer(StdDeserializer<?> src) {
        super(src);
    }

    public ExerciseDeserializer(){
        super(Exercise.class);
    }

    @Override
    public Exercise deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        int id = node.get("id").asInt();
        String name = node.get("name").asText();
        String descr = node.get("description").asText();
        return new Exercise(id, name, descr);
    }
}
