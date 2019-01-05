package com.devop.aashish.annotationhelper.database;

import android.arch.persistence.room.RoomDatabase;

import com.devop.aashish.annotationhelper.database.entity.TestEntity;
import com.devop.aashish.annotationhelper.database.entity.TestEntityAnother;


@android.arch.persistence.room.Database(entities = {
        TestEntity.class
        , TestEntityAnother.class
}, version = 1)

public abstract class AppDatabase extends RoomDatabase {


}