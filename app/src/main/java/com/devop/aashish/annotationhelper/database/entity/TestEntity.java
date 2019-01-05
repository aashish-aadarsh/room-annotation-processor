package com.devop.aashish.annotationhelper.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.devop.aashish.annotation.GenerateDao;
import com.devop.aashish.annotation.GenerateMethod;

import java.util.Date;

/**
 * Created by Aashish Aadarsh on 3/7/2018.
 */

@Entity(tableName = "user")
@GenerateDao(generateAllField = true, generateDeleteAllField = true, generateAllFieldIn = true)
public class TestEntity {

    @PrimaryKey(autoGenerate = true)
    Long id;

    @ColumnInfo(name = "first_name")
    @GenerateMethod(generateDelete = true,generateGetIn = true)
    String firstName;

    @ColumnInfo(name = "last_name")
    @GenerateMethod(generateDelete = true,generateGet = false,generateGetIn = true)
    String lastName;

    @ColumnInfo(name = "roll_number")
    Integer rollNo;

    @GenerateMethod(generateDelete = true,generateGet = false,generateGetIn = true)
    @ColumnInfo(name = "marks")
    Double marks;

    @ColumnInfo(name = "hours_spent")
    Float noOfHours;

    @ColumnInfo(name = "enroll_id")
    Long enrollmentNumber;

    @ColumnInfo(name = "is_active")
    Boolean isActive;

    @ColumnInfo(name = "roll_prim")
    int rollPrimitive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getRollNo() {
        return rollNo;
    }

    public void setRollNo(Integer rollNo) {
        this.rollNo = rollNo;
    }

    public Double getMarks() {
        return marks;
    }

    public void setMarks(Double marks) {
        this.marks = marks;
    }

    public Float getNoOfHours() {
        return noOfHours;
    }

    public void setNoOfHours(Float noOfHours) {
        this.noOfHours = noOfHours;
    }

    public Long getEnrollmentNumber() {
        return enrollmentNumber;
    }

    public void setEnrollmentNumber(Long enrollmentNumber) {
        this.enrollmentNumber = enrollmentNumber;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public int getRollPrimitive() {
        return rollPrimitive;
    }

    public void setRollPrimitive(int rollPrimitive) {
        this.rollPrimitive = rollPrimitive;
    }
}
