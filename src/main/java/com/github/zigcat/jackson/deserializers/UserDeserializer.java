package com.github.zigcat.jackson.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.zigcat.exceptions.EmailException;
import com.github.zigcat.ormlite.models.Role;
import com.github.zigcat.ormlite.models.TrainingType;
import com.github.zigcat.ormlite.models.User;
import com.github.zigcat.ormlite.services.Security;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.time.LocalDate;

public class UserDeserializer extends StdDeserializer<User> {
    protected UserDeserializer(Class<?> vc) {
        super(vc);
    }

    protected UserDeserializer(JavaType valueType) {
        super(valueType);
    }

    protected UserDeserializer(StdDeserializer<?> src) {
        super(src);
    }

    public UserDeserializer(){
        super(User.class);
    }

    @Override
    public User deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        int id = node.get("id").asInt();
        String name = node.get("name").asText();
        String surname = node.get("surname").asText();
        String email = node.get("email").asText();
        if(!Security.isValidEmail(email)){
            throw new EmailException("wrong email");
        }
        String password = node.get("password").asText();
        double height = node.get("height").asDouble();
        double weight = node.get("weight").asDouble();
        String trainingType = node.get("trainingType").asText();
        double workingWeight = node.get("workingWeight").asDouble();
        int age = node.get("age").asInt();
        double maxCalories = 267.98 + (11.3 * weight) + (3.92 * height * 100) - (5 * age);
        return new User(id, name, surname, email, BCrypt.hashpw(password, BCrypt.gensalt(12)),
                height, weight, age, TrainingType.valueOf(trainingType), Role.USER, workingWeight,
                LocalDate.now().format(User.dateTimeFormatter), maxCalories);
    }
}
