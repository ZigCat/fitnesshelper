package com.github.zigcat.jackson.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.zigcat.ormlite.models.ExerciseUser;

import java.io.IOException;

public class ExerciseUserSerializer extends StdSerializer<ExerciseUser> {
    protected ExerciseUserSerializer(Class<ExerciseUser> t) {
        super(t);
    }

    protected ExerciseUserSerializer(JavaType type) {
        super(type);
    }

    protected ExerciseUserSerializer(Class<?> t, boolean dummy) {
        super(t, dummy);
    }

    protected ExerciseUserSerializer(StdSerializer<?> src) {
        super(src);
    }

    public ExerciseUserSerializer(){
        super(ExerciseUser.class);
    }

    @Override
    public void serialize(ExerciseUser exerciseUser, JsonGenerator json, SerializerProvider serializerProvider) throws IOException {
        json.writeStartObject();
        json.writeNumberField("id", exerciseUser.getId());
        json.writeObjectField("exercise", exerciseUser.getExercise());
        json.writeObjectField("user", exerciseUser.getUser());
        json.writeNumberField("laps", exerciseUser.getLaps());
        json.writeNumberField("counts", exerciseUser.getCounts());
        json.writeNumberField("heaviness", exerciseUser.getHeaviness());
        json.writeEndObject();
    }
}
