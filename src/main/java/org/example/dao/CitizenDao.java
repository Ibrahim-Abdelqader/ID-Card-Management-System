package org.example.dao;

import org.example.model.Citizen;

import java.util.List;

// a class to define CRUD++ functions
public interface CitizenDao {
    // all function needed for database
    List<Citizen> findAll();            // return all data exist in the database

    Citizen findById(String id);        // find a citizen by ID

    String[] getNameByID(String id);    // find citizen's name by ID

    void create(Citizen person);        // insert a new person in database

    void update(Citizen person);        // update data of citizen

    void deleteByID(String id);         // delete a citizen from database by ID

    boolean isIdExist(String id);       // check if ID exist in database

}
