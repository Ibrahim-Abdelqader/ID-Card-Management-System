package org.example.model;

import org.example.dao.CitizenDao;
import org.example.dao.CitizenDaoImpl;

import java.time.LocalDate;
import java.time.Period;

public class Citizen {
    private String id;
    private String firstName;
    private String lastName;
    private LocalDate birthdate;
    private int age;
    private Gender gender;
    private MaritalState maritalState;
    private Religion religion;
    private String husbandName;
    private String occupation;      // job
    private String state;
    private String address;
    private LocalDate creatingDate;
    private LocalDate expirationDate;

    public enum Gender {
        FEMALE, MALE
    }

    public enum MaritalState {
        SINGLE, MARRIED, DIVORCED, WIDOWED
    }

    public enum Religion {
        ISLAM, CHRISTIANITY, OTHER
    }

    public Citizen() {
        this.firstName = null;
        this.lastName = null;
        this.birthdate = null;
        this.id = null;
        this.age = 0;
        this.occupation = null;
        this.state = null;
        this.address = null;
        this.creatingDate = LocalDate.now();
        this.expirationDate = this.creatingDate.plusYears(7);
    }

    // Setters & getters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setId() {
        int randomNumber, century;
        String randomPart, newID;
        CitizenDao citizenDao = new CitizenDaoImpl();

        // Determine the century (2 for 1900s, 3 for 2000s)
        century = (birthdate.getYear() >= 2000) ? 3 : 2;

        // Format the birthdate as YYMMDD (6 digits)
        String birthDatePart = String.format("%02d%02d%02d",
                birthdate.getYear() % 100,          // Last two digits of the year
                birthdate.getMonthValue(),          // Month
                birthdate.getDayOfMonth());         // Day

        // Generate a unique random ID
        do {
            randomNumber = (int) (Math.random() * 1_000_0000); // Generates a number between 0 and 9999999
            randomPart = String.format("%07d", randomNumber); // Ensure it’s 7 digits
            newID = century + birthDatePart + randomPart;
        } while (citizenDao.isIdExist(newID));
        // Combine the century, birthdate, and random number to form the ID
        id = newID;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge() {
        if (birthdate != null) {
            LocalDate currentDate = LocalDate.now();
            Period period = Period.between(birthdate, currentDate);
            this.age = period.getYears(); // Set age based on birthdate
        }
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
        setAge();
    }

    public LocalDate getCreatingDate() {
        return creatingDate;
    }

    public void setCreatingDate(LocalDate creatingDate) {
        this.creatingDate = creatingDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public MaritalState getMaritalState() {
        return maritalState;
    }

    public void setMaritalState(MaritalState maritalState) {
        this.maritalState = maritalState;
    }

    public Religion getReligion() {
        return religion;
    }

    public void setReligion(Religion religion) {
        this.religion = religion;
    }

    public String getHusbandName() {
        return husbandName;
    }

    public void setHusbandName(String husbandName) {
        this.husbandName = husbandName;
    }

    public void printDetails() {
        System.out.println("First Name: " + firstName);
        System.out.println("Last Name: " + lastName);
        System.out.println("Age: " + age);
        System.out.println("ID: " + id);
    }

    public String toString() {
        return "Citizen{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthdate=" + birthdate +
                ", age=" + age +
                ", gender=" + gender +
                ", religion=" + religion +
                ", maritalState=" + maritalState +
                ", husbandName='" + husbandName + '\'' +
                ", occupation='" + occupation + '\'' +
                ", state='" + state + '\'' +
                ", address='" + address + '\'' +
                ", creatingDate=" + creatingDate +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
