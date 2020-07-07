package com.github.zigcat.ormlite.parameters;

import com.github.zigcat.ormlite.models.User;

public interface Creatable extends FullModelable{
    void setUser(User u);
    User getUser();
}
