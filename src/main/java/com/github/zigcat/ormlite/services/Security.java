package com.github.zigcat.ormlite.services;

import com.github.zigcat.ormlite.models.User;
import org.apache.commons.validator.routines.EmailValidator;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

public class Security {
    public static String badRequestMessage = "Stopped by client's bad request(400)";
    public static String emailMessage = "Stopped by wrong email(400)";
    public static String serverErrorMessage = "Stopped by server error(500)";
    public static String queryParamMessage = "Stopped by wrong query param(400)";
    public static String forbiddenMessage = "Stopped by unauthorized/forbidden request";

    public static User authorize(String login, String password) throws SQLException {
        for(User u: User.controller.getService().listAll(User.controller.getDao())){
            if(u.getEmail().equals(login) && BCrypt.checkpw(password, u.getPassword())){
                return u;
            }
        }
        return null;
    }

    public static boolean isValidEmail(String email){
        email = email.trim();
        EmailValidator emailValidator = EmailValidator.getInstance();
        if(emailValidator.isValid(email)){
            return true;
        }
        return false;
    }
}
