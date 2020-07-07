package com.github.zigcat.ormlite.services;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Pagination {
    private static Logger l = LoggerFactory.getLogger(Pagination.class);
    public Pagination(){}

    public <T> List<T> pagination(Dao<T, Integer> dao, long page, long pageSize) throws SQLException {
        QueryBuilder<T, Integer> queryBuilder = dao.queryBuilder();
        queryBuilder.offset(page * pageSize).limit(pageSize);
        return dao.query(queryBuilder.prepare());
    }

    public <T> List<T> basicPagination(List<T> tList, long page, long pageSize){
        l.info("Starting pagination");
        ArrayList<T> list = new ArrayList<>();
        try{
            for(int i=(int)(page-1)*(int)pageSize;i<tList.size();i++){
                list.add(tList.get(i));
            }
        } catch(IndexOutOfBoundsException e){
            l.info("List contains low quantity of elements");
        } finally {
            l.info("pagination DONE");
            return list;
        }
    }
}
