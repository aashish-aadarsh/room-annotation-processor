package com.devop.aashish.annotationhelper.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.devop.aashish.annotation.GenerateDao;

/**
 * Created by Aashish Aadarsh on 3/7/2018.
 */

@Entity(tableName = "test")
@GenerateDao(generateAllField = true)
public class TestEntityAnother {

    @PrimaryKey(autoGenerate = true)
    Long id;

    @ColumnInfo
    String columnOne;

    @ColumnInfo(name = "column_2")
    String columnTwo;

    public String getColumnOne() {
        return columnOne;
    }

    public void setColumnOne(String columnOne) {
        this.columnOne = columnOne;
    }

    public String getColumnTwo() {
        return columnTwo;
    }

    public void setColumnTwo(String columnTwo) {
        this.columnTwo = columnTwo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
