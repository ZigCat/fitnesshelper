package com.github.zigcat.jackson.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.zigcat.ormlite.models.User;

import java.io.IOException;

public class UserAdminSerializer extends StdSerializer<User> {

    protected UserAdminSerializer(Class<User> t) {
        super(t);
    }

    protected UserAdminSerializer(JavaType type) {
        super(type);
    }

    protected UserAdminSerializer(Class<?> t, boolean dummy) {
        super(t, dummy);
    }

    protected UserAdminSerializer(StdSerializer<?> src) {
        super(src);
    }

    public UserAdminSerializer(){
        super(User.class);
    }

    @Override
    public void serialize(User user, JsonGenerator json, SerializerProvider serializerProvider) throws IOException {
        json.writeStartObject();
        json.writeNumberField("id", user.getId());
        json.writeStringField("name", user.getName());
        json.writeStringField("surname", user.getSubparam());
        json.writeStringField("email", user.getEmail());
        json.writeNumberField("weight", user.getWeight());
        json.writeNumberField("height", user.getHeight());
        json.writeNumberField("age", user.getAge());
        json.writeStringField("trainingType", user.getTrainingType().toString());
        json.writeStringField("role", user.getRole().toString());
        json.writeNumberField("workingWeight", user.getWorkingWeight());
        json.writeNumberField("maxCalories", user.getMaxCalories());
        json.writeStringField("regDate", user.getRegistrationDate());
        json.writeEndObject();
    }
}
