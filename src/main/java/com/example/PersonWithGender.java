package com.example;

/**
 * Created by z003rv2s on 27.01.2017.
 */
public class PersonWithGender extends Person {

    private String gender;

    public PersonWithGender(String gender) {
        this.gender = gender;
    }

    public PersonWithGender(int age, String name, String gender) {
        super(age, name);
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "PersonWithGender{" +
                "gender='" + gender + '\'' +
                "} " + super.toString();
    }
}
