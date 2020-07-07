package com.github.zigcat.ormlite.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.zigcat.exceptions.RedirectedException;
import com.github.zigcat.ormlite.models.Role;
import com.github.zigcat.ormlite.models.User;
import com.github.zigcat.ormlite.parameters.Creatable;
import com.github.zigcat.ormlite.parameters.FullModelable;
import com.github.zigcat.ormlite.services.Security;
import io.javalin.http.Context;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class SpecificCreateController<T extends Creatable> extends Controller<T> {
    public SpecificCreateController(Class<T> valueClass) {
        super(valueClass);
    }

    @Override
    public void create(Context ctx, ObjectMapper om){
        String login = ctx.basicAuthCredentials().getUsername();
        String password = ctx.basicAuthCredentials().getPassword();
        l.info("@CREATING MODEL@");
        try {
            User u = Security.authorize(login, password);
            if(u != null){
                T t = om.readValue(ctx.body(), getValueClass());
                t.setUser(u);
                getDao().create(t);
                ctx.status(201);
                ctx.result(om.writeValueAsString(t));
                l.info("created "+t.toString());
            }
        } catch (SQLException | RedirectedException e) {
            e.printStackTrace();
            ctx.status(500);
            l.info(Security.serverErrorMessage);
            ctx.result(Security.serverErrorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            ctx.status(400);
            ctx.result(Security.badRequestMessage);
            l.info(Security.badRequestMessage);
        }
        l.info("@QUERY DONE@");
    }

    @Override
    public void getAll(Context ctx, ObjectMapper om){
        l.info("@GETTING FOOD INFO@");
        String login = ctx.basicAuthCredentials().getUsername();
        String password = ctx.basicAuthCredentials().getPassword();
        Map map = ctx.queryParamMap();
        boolean isAdmin = false;
        try{
            User u = Security.authorize(login, password);
            if(u.getRole().equals(Role.ADMIN)){
                isAdmin = true;
            }
            List<T> fList = getService().listAll(getDao());
            ArrayList<T> tList = new ArrayList<>();
            for(T t1: fList){
                if(t1.getUser().getId() == u.getId() || t1.getUser().getRole().equals(Role.ADMIN) || isAdmin){
                    tList.add(t1);
                }
            }
            if(map.containsKey("name")
                    && getValueClass().getName().equalsIgnoreCase("com.github.zigcat.ormlite.models.Food")){
                l.info("GET models info by name");
                ArrayList<T> foundT = new ArrayList<>();
                String name = ctx.queryParam("name");
                for(T t3: tList){
                    if(t3.getName().equalsIgnoreCase(name) || t3.getName().toLowerCase().contains(name.toLowerCase())){
                        foundT.add(t3);
                    }
                }
                ctx.status(200);
                ctx.result(om.writeValueAsString(foundT));
            } else if(map.containsKey("mode")
                    && getValueClass().getName().equalsIgnoreCase("com.github.zigcat.ormlite.models.Food")) {
                if (ctx.queryParam("mode").equalsIgnoreCase("alphabet")) {
                    l.info("GET models by alphabet");
                    tList.sort(Comparator.comparing(FullModelable::getName));
                    ctx.status(200);
                    ctx.result(om.writeValueAsString(pagination.basicPagination(tList, 1, 10)));
                }
            } else if(map.containsKey("id")){
                l.info("GET model by id");
                int id;
                if(map.containsKey("id")){
                    id = Integer.parseInt(ctx.queryParam("id"));
                } else {
                    throw new NullPointerException();
                }
                T t2 = getService().getById(getDao(), id);
                ctx.status(200);
                boolean isAccepted = false;
                for(T t1: tList){
                    if(t1.getId() == t2.getId()){
                        ctx.result(om.writeValueAsString(t2));
                        isAccepted = true;
                    }
                }
                if(!isAccepted){
                    ctx.result("{}");
                }
            } else {
                l.info("GET models info simplify");
                ctx.status(200);
                ctx.result(om.writeValueAsString(pagination.basicPagination(tList, 1, 10)));
            }
        } catch(NullPointerException e){
            ctx.status(400);
            ctx.result(Security.badRequestMessage);
            l.warn(Security.badRequestMessage);
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
}
