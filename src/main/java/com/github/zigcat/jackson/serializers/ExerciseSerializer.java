package com.github.zigcat.jackson.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.zigcat.ormlite.models.Exercise;

import java.io.IOException;

public class ExerciseSerializer extends StdSerializer<Exercise> {
    protected ExerciseSerializer(Class<Exercise> t) {
        super(t);
    }

    protected ExerciseSerializer(JavaType type) {
        super(type);
    }

    protected ExerciseSerializer(Class<?> t, boolean dummy) {
        super(t, dummy);
    }

    protected ExerciseSerializer(StdSerializer<?> src) {
        super(src);
    }

    public ExerciseSerializer(){
        super(Exercise.class);
    }

    @Override
    public void serialize(Exercise exercise, JsonGenerator json, SerializerProvider serializerProvider) throws IOException {
        json.writeStartObject();
        json.writeNumberField("id", exercise.getId());
        json.writeStringField("name", exercise.getName());
        json.writeStringField("description", exercise.getDescription());
        json.writeEndObject();
    }
}
