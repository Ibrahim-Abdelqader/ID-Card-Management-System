package org.example.dao;


import org.example.model.Citizen;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

// a class to implement all functions needed for database
public class CitizenDaoImpl implements CitizenDao {
    @Override
    public List<Citizen> findAll() {
        Connection con = org.example.dao.DBconnection.getConnection();  // obtain a connection to database

        if (con == null) {
            System.err.println("Database connection is null.");
            return null;
        }

        List<Citizen> citizens = new LinkedList<>();    // list of all people exist in database

        String query = "SELECT * FROM citizen;";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // set all data to the list to show them
                Citizen citizen = new Citizen();
                citizen.setId(resultSet.getString("ID"));
                citizen.setFirstName(resultSet.getString("firstName"));
                citizen.setLastName(resultSet.getString("lastName"));
                citizen.setBirthdate(resultSet.getDate("birthDate").toLocalDate());
                citizen.setAge();
                citizen.setGender(Citizen.Gender.valueOf(resultSet.getString("gender")));
                citizen.setReligion(Citizen.Religion.valueOf(resultSet.getString("religion")));
                citizen.setMaritalState(Citizen.MaritalState.valueOf(resultSet.getString("MaritalState")));
                citizen.setHusbandName(resultSet.getString("husbandName"));
                citizen.setOccupation(resultSet.getString("occupation"));
                citizen.setState(resultSet.getString("state"));
                citizen.setAddress(resultSet.getString("address"));
                citizen.setCreatingDate(resultSet.getDate("creatingDate").toLocalDate());
                citizen.setExpirationDate(resultSet.getDate("exDate").toLocalDate());

                citizens.add(citizen);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return citizens;
    }

    @Override
    public Citizen findById(String id) {
        Connection con = org.example.dao.DBconnection.getConnection();
        if (con == null) {
            System.err.println("Database connection is null.");
            return null;
        }
        String query = "SELECT * FROM citizen WHERE ID = ?;";   // select all data of satisfied ID
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Citizen citizen = new Citizen();
                citizen.setFirstName(resultSet.getString("firstName"));
                citizen.setLastName(resultSet.getString("lastName"));
                citizen.setBirthdate(resultSet.getDate("birthDate").toLocalDate());
                citizen.setAge();
                citizen.setGender(Citizen.Gender.valueOf(resultSet.getString("gender")));
                citizen.setReligion(Citizen.Religion.valueOf(resultSet.getString("religion")));
                citizen.setMaritalState(Citizen.MaritalState.valueOf(resultSet.getString("MaritalState")));
                citizen.setHusbandName(resultSet.getString("husbandName"));
                citizen.setOccupation(resultSet.getString("occupation"));
                citizen.setState(resultSet.getString("state"));
                citizen.setAddress(resultSet.getString("address"));
                citizen.setCreatingDate(resultSet.getDate("creatingDate").toLocalDate());
                citizen.setExpirationDate(resultSet.getDate("exDate").toLocalDate());

                return citizen;
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public String[] getNameByID(String id) {
        Connection con = org.example.dao.DBconnection.getConnection();
        if (con == null) {
            System.err.println("Database connection is null.");
            return null;
        }

        String query = "SELECT firstName, lastName FROM citizen WHERE ID = ?;";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Extract first and last names
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");

                return new String[]{firstName, lastName};
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null; // Return null if ID does not exist or an error occurs
    }


    @Override
    public void create(Citizen person) {
        Connection con = org.example.dao.DBconnection.getConnection();
        if (con == null) {
            System.err.println("Database connection is null.");
            return;
        }
        {
            String query = "INSERT INTO citizen (ID, firstName, lastName, birthDate, age, gender, religion, " +
                    "MaritalState, husbandName, occupation, state, address, creatingDate, exDate) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

            try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
                preparedStatement.setString(1, person.getId());
                preparedStatement.setString(2, person.getFirstName());
                preparedStatement.setString(3, person.getLastName());
                preparedStatement.setDate(4, Date.valueOf(person.getBirthdate()));
                preparedStatement.setInt(5, person.getAge());
                preparedStatement.setString(6, person.getGender().toString());
                preparedStatement.setString(7, person.getReligion().toString());
                preparedStatement.setString(8, person.getMaritalState().toString());
                preparedStatement.setString(9, person.getHusbandName());
                preparedStatement.setString(10, person.getOccupation());
                preparedStatement.setString(11, person.getState());
                preparedStatement.setString(12, person.getAddress());
                preparedStatement.setDate(13, Date.valueOf(person.getCreatingDate()));
                preparedStatement.setDate(14, Date.valueOf(person.getExpirationDate()));

                preparedStatement.executeUpdate();
            } catch (SQLException se) {
                se.printStackTrace();
            } finally {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void update(Citizen person) {
        Connection con = org.example.dao.DBconnection.getConnection();
        if (con == null) {
            System.err.println("Database connection is null.");
            return;
        }

        String query = "UPDATE citizen SET firstName=? , lastName=? ,birthDate=?,age=? , gender=?"
                + " ,religion =?,MaritalState=?,husbandName=?,occupation=?,state=?,address=? WHERE ID=?;";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {

            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getLastName());
            preparedStatement.setDate(3, Date.valueOf(person.getBirthdate()));
            preparedStatement.setInt(4, person.getAge());
            preparedStatement.setString(5, person.getGender().toString());
            preparedStatement.setString(6, person.getReligion().toString());
            preparedStatement.setString(7, person.getMaritalState().toString());
            preparedStatement.setString(8, person.getHusbandName());
            preparedStatement.setString(9, person.getOccupation());
            preparedStatement.setString(10, person.getState());
            preparedStatement.setString(11, person.getAddress());
            preparedStatement.setString(12, person.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteByID(String id) {
        Connection con = org.example.dao.DBconnection.getConnection();

        if (con == null) {
            System.err.println("Database connection is null.");
            return;
        }

        String query = "DELETE FROM citizen WHERE ID = ?;";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean isIdExist(String ID) {
        Connection con = org.example.dao.DBconnection.getConnection();
        if (con == null) {
            System.err.println("Database connection is null.");
            return false;
        }

        String query = "SELECT 1 FROM citizen WHERE ID = ? LIMIT 1;";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, ID);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                return true;
            }
        } catch (SQLException se) {
            se.printStackTrace();

        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }


}

