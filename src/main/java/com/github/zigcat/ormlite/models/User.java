package com.github.zigcat.ormlite.models;


import com.github.zigcat.ormlite.controllers.SpecificSearchController;
import com.github.zigcat.ormlite.parameters.FullModelable;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.time.format.DateTimeFormatter;

@DatabaseTable(tableName = "user")
public class User implements FullModelable {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String name;

    @DatabaseField(columnName = "surname")
    private String subParam;

    @DatabaseField
    private String email;

    @DatabaseField
    private String password;

    @DatabaseField
    private double height;

    @DatabaseField
    private double weight;

    @DatabaseField
    private int age;

    @DatabaseField
    private double maxCalories;

    @DatabaseField(dataType = DataType.ENUM_STRING)
    private TrainingType trainingType;

    @DatabaseField(dataType = DataType.ENUM_STRING)
    private Role role;

    @DatabaseField
    private double workingWeight;

    @DatabaseField
    private String registrationDate;

    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("YYYY MM dd");
    public static SpecificSearchController<User> controller = new SpecificSearchController<>(User.class);

    public User() {
    }

    public User(int id, String name, String subParam, String email, String password, double height, double weight, int age, TrainingType trainingType, Role role, double workingWeight, String registrationDate, double maxCalories) {
        this.id = id;
        this.name = name;
        this.subParam = subParam;
        this.email = email;
        this.password = password;
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.trainingType = trainingType;
        this.role = role;
        this.workingWeight = workingWeight;
        this.registrationDate = registrationDate;
        this.maxCalories = maxCalories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubparam() {
        return subParam;
    }

    public void setSubParam(String subParam) {
        this.subParam = subParam;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public TrainingType getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(TrainingType trainingType) {
        this.trainingType = trainingType;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public double getWorkingWeight() {
        return workingWeight;
    }

    public void setWorkingWeight(double workingWeight) {
        this.workingWeight = workingWeight;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public double getMaxCalories() {
        return maxCalories;
    }

    public void setMaxCalories(double maxCalories) {
        this.maxCalories = maxCalories;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subParam='" + subParam + '\'' +
                '}';
    }
}
