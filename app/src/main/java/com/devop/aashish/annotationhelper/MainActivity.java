package com.devop.aashish.annotationhelper;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.devop.aashish.annotationhelper.database.AppDatabase;
import com.devop.aashish.annotationhelper.database.entity.TestEntityAnother;


public class MainActivity extends AppCompatActivity {

    private static AppDatabase mAppDatabase;

    public static void provideDatabase(Context context) {
        if (mAppDatabase == null) {
            mAppDatabase = Room.databaseBuilder(context, AppDatabase.class, "app_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        provideDatabase(this);
        TestEntityAnother testEntity = new TestEntityAnother();
        testEntity.setColumnOne("Aashish");
        testEntity.setColumnTwo("Aadarsh");


    }
}
