package com.github.zigcat.ormlite.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.zigcat.*;
import com.github.zigcat.exceptions.EmailException;
import com.github.zigcat.exceptions.RedirectedException;
import com.github.zigcat.ormlite.models.Role;
import com.github.zigcat.ormlite.models.User;
import com.github.zigcat.ormlite.parameters.Modelable;
import com.github.zigcat.ormlite.services.Pagination;
import com.github.zigcat.ormlite.services.Security;
import com.github.zigcat.ormlite.services.Service;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Controller<T extends Modelable> {
    private Service<T> service;
    private Dao<T, Integer> dao;
    private Class<T> valueClass;
    protected Logger l;
    protected Pagination pagination;

    public Controller(Class<T> valueClass){
        this.l = LoggerFactory.getLogger(valueClass);
        this.service = new Service<T>();
        this.valueClass = valueClass;
        this.pagination = new Pagination();
        initDao(valueClass);
    }

    public void getAll(Context ctx, ObjectMapper om){
        l.info("@GETTING MODELS INFO@");
        Map map = ctx.queryParamMap();
        long page = 0;
        List<T> ts;
        T t;
        try{
            if(map.containsKey("page")){
                page = Long.parseLong(ctx.queryParam("page"));
            }
            if(map.containsKey("searchType")){
                if ("3".equals(ctx.queryParam("searchType"))) {
                    l.info("GET model by id");
                    int id;
                    if (map.containsKey("id")) {
                        id = Integer.parseInt(ctx.queryParam("id"));
                    } else {
                        throw new NullPointerException();
                    }
                    T t2 = getService().getById(getDao(), id);
                    ctx.status(200);
                    ctx.result(om.writeValueAsString(t2));
                }
            } else {
                l.info("GET models info simplify");
                ctx.status(200);
                ctx.result(om.writeValueAsString(pagination.pagination(getDao(), page, 10)));
            }
        } catch(NullPointerException e){
            ctx.status(400);
            ctx.result(Security.queryParamMessage);
            l.warn(Security.queryParamMessage);
        } catch (SQLException | RedirectedException e) {
            e.printStackTrace();
            ctx.status(500);
            ctx.result(Security.serverErrorMessage);
            l.warn(Security.serverErrorMessage);
        } catch (JsonProcessingException e){
            e.printStackTrace();
            ctx.status(400);
            l.info(Security.badRequestMessage);
            ctx.result(Security.badRequestMessage);
        }
        l.info("@QUERY DONE@");
    }

    public void create(Context ctx, ObjectMapper om){
        String login = ctx.basicAuthCredentials().getUsername();
        String password = ctx.basicAuthCredentials().getPassword();
        l.info("@CREATING MODEL@");
        try {
            User u = Security.authorize(login, password);
            if((u == null || u.getRole().equals(Role.ADMIN)) ||
                        getValueClass().getName().equalsIgnoreCase("com.github.zigcat.ormlite.models.User")){
                    T t = om.readValue(ctx.body(), getValueClass());
                    getDao().create(t);
                    l.info("created "+t.toString());
                    ctx.status(201);
                    ctx.result(om.writeValueAsString(t));
            } else {
                ctx.status(403);
                ctx.result(Security.forbiddenMessage);
                l.info(Security.forbiddenMessage);
            }
        } catch (SQLException | RedirectedException e) {
            e.printStackTrace();
            ctx.status(500);
            l.info(Security.serverErrorMessage);
            ctx.result(Security.serverErrorMessage);
        } catch(EmailException e){
            ctx.status(400);
            l.info(Security.emailMessage);
            ctx.result(Security.emailMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            ctx.status(400);
            ctx.result(Security.badRequestMessage);
            l.info(Security.badRequestMessage);
        }
        l.info("@QUERY DONE@");
    }

    public void update(Context ctx, ObjectMapper om){
        l.info("@UPDATING MODEL@");
        String login = ctx.basicAuthCredentials().getUsername();
        String password = ctx.basicAuthCredentials().getPassword();
        try {
            T t = om.readValue(ctx.body(), getValueClass());
            User u = Security.authorize(login, password);
            if((getValueClass().getName().equalsIgnoreCase("com.github.zigcat.ormlite.models.User")
                    && u.getId() == t.getId())
                    || u.getRole().equals(Role.ADMIN)){
                getDao().update(t);
                ctx.status(200);
                ctx.result(om.writeValueAsString(t));
                l.info("updating "+t.toString());
            } else {
                ctx.status(403);
                ctx.result(Security.forbiddenMessage);
                l.info(Security.forbiddenMessage);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            ctx.status(400);
            ctx.result(Security.badRequestMessage);
            l.info(Security.badRequestMessage);
        } catch (SQLException | RedirectedException e) {
            e.printStackTrace();
            ctx.status(500);
            l.info(Security.serverErrorMessage);
            ctx.result(Security.serverErrorMessage);
        } catch(EmailException e){
            ctx.status(400);
            l.info(Security.emailMessage);
            ctx.result(Security.emailMessage);
        }
        l.info("@QUERY DONE@");
    }

    public void delete(Context ctx){
        l.info("@DELETING MODEL@");
        String login = ctx.basicAuthCredentials().getUsername();
        String password = ctx.basicAuthCredentials().getPassword();
        int id = Integer.parseInt(ctx.pathParam("id"));
        try {
            User u = Security.authorize(login, password);
            if((getValueClass().getName().equalsIgnoreCase("com.github.zigcat.ormlite.models.User")
                    && u.getId() == id)
                    || u.getRole().equals(Role.ADMIN)){
                getDao().deleteById(id);
                ctx.status(200);
                ctx.result("model with id="+id+"has successfully deleted");
                l.info("model with id="+id+"has successfully deleted");
            } else {
                ctx.status(403);
                ctx.result(Security.forbiddenMessage);
                l.info(Security.forbiddenMessage);
            }
        } catch (SQLException | RedirectedException e) {
            e.printStackTrace();
            ctx.status(500);
            l.info(Security.serverErrorMessage);
            ctx.result(Security.serverErrorMessage);
        }
        l.info("@QUERY DONE@");
    }

    private void initDao(Class<T> valueClass){
        try {
            this.dao = DaoManager.createDao(DatabaseConfiguration.source, valueClass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Service<T> getService() {
        return service;
    }

    public void setService(Service<T> service) {
        this.service = service;
    }

    public Dao<T, Integer> getDao() {
        return dao;
    }

    public void setDao(Dao<T, Integer> dao) {
        this.dao = dao;
    }

    public Class<T> getValueClass() {
        return valueClass;
    }

    public void setValueClass(Class<T> valueClass) {
        this.valueClass = valueClass;
    }
}
