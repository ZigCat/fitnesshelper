package com.github.zigcat.ormlite.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.zigcat.exceptions.RedirectedException;
import com.github.zigcat.ormlite.models.Role;
import com.github.zigcat.ormlite.models.User;
import com.github.zigcat.ormlite.parameters.FullModelable;
import com.github.zigcat.ormlite.services.Security;
import io.javalin.http.Context;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class SpecificSearchController<T extends FullModelable> extends Controller<T>{
    public SpecificSearchController(Class<T> valueClass) {
        super(valueClass);
    }

    public void getAll(Context ctx, ObjectMapper om, ObjectMapper omAdmin){
        l.info("@GETTING MODELS INFO@");
        String login = ctx.basicAuthCredentials().getUsername();
        String password = ctx.basicAuthCredentials().getPassword();
        boolean isAdmin = false;
        Map map = ctx.queryParamMap();
        long page = 0;
        List<T> ts;
        T t;
        try{
            User u = Security.authorize(login, password);
            l.info("checking access");
            if(u != null && u.getRole().equals(Role.ADMIN)){
                l.info("USER has admin access");
                isAdmin = true;
            }
            l.info("getting page");
            if(map.containsKey("page")){
                page = Long.parseLong(ctx.queryParam("page"));
                l.info("page = "+page);
            }
            l.info("getting searchType");
            if(map.containsKey("searchType")){
                switch (ctx.queryParam("searchType")){
                    case "1":
                        l.info("searchType = 1");
                        l.info("GET models by alphabet");
                        List<T> tList = getService().listAll(getDao());
                        tList.sort(Comparator.comparing(FullModelable::getName));
                        ctx.status(200);
                        if(isAdmin){
                            ctx.result(omAdmin.writeValueAsString(pagination.basicPagination(tList, page-1, 10)));
                        } else {
                            ctx.result(om.writeValueAsString(pagination.basicPagination(tList, page-1, 10)));
                        }
                        break;
                    case "2":
                        l.info("GET models info by parameters");
                        ArrayList<T> foundT = new ArrayList<>();
                        String name = "";
                        if(map.containsKey("name")){
                            name = ctx.queryParam("name");
                        }
                        for(T t1: getService().listAll(getDao())){
                            if(t1.getName().equalsIgnoreCase(name)){
                                foundT.add(t1);
                            }
                        }
                        ctx.status(200);
                        if(isAdmin){
                            ctx.result(omAdmin.writeValueAsString(pagination.basicPagination(foundT, page-1, 10)));
                        } else {
                            ctx.result(om.writeValueAsString(pagination.basicPagination(foundT, page-1, 10)));
                        }
                        break;
                    case "3":
                        l.info("GET model by id");
                        int id;
                        if(map.containsKey("id")){
                            id = Integer.parseInt(ctx.queryParam("id"));
                        } else {
                            throw new NullPointerException();
                        }
                        T t2 = getService().getById(getDao(), id);
                        ctx.status(200);
                        if(isAdmin){
                            ctx.result(omAdmin.writeValueAsString(t2));
                        } else {
                            ctx.result(om.writeValueAsString(t2));
                        }
                        break;
                }
            } else {
                l.info("GET models info simplify");
                ctx.status(200);
                if(isAdmin){
                    ctx.result(omAdmin.writeValueAsString(pagination.pagination(getDao(), page-1, 10)));
                } else {
                    ctx.result(om.writeValueAsString(pagination.pagination(getDao(), page-1, 10)));
                }
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
