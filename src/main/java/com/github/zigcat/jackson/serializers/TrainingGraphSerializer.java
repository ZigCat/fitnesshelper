package com.github.zigcat.jackson.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.zigcat.ormlite.models.TrainingGraph;

import java.io.IOException;

public class TrainingGraphSerializer extends StdSerializer<TrainingGraph> {
    protected TrainingGraphSerializer(Class<TrainingGraph> t) {
        super(t);
    }

    protected TrainingGraphSerializer(JavaType type) {
        super(type);
    }

    protected TrainingGraphSerializer(Class<?> t, boolean dummy) {
        super(t, dummy);
    }

    protected TrainingGraphSerializer(StdSerializer<?> src) {
        super(src);
    }

    public TrainingGraphSerializer(){
        super(TrainingGraph.class);
    }

    @Override
    public void serialize(TrainingGraph trainingGraph, JsonGenerator json, SerializerProvider serializerProvider) throws IOException {
        json.writeStartObject();
        json.writeNumberField("id", trainingGraph.getId());
        json.writeObjectField("exerciseUser", trainingGraph.getExerciseUser());
        json.writeStringField("dayOfWeek", trainingGraph.getDayOfWeek().toString());
        json.writeEndObject();
    }
}
