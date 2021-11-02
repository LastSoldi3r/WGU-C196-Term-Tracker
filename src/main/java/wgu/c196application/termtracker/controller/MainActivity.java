package wgu.c196application.termtracker.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import wgu.c196application.termtracker.R;
import wgu.c196application.termtracker.database.Repository;
import wgu.c196application.termtracker.model.AssessmentEntity;
import wgu.c196application.termtracker.model.CourseEntity;
import wgu.c196application.termtracker.model.TermEntity;

public class MainActivity extends AppCompatActivity {

    public static int numAlert = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void enterHere(View view) {
        Intent intent = new Intent(MainActivity.this, TermActivity.class);
        startActivity(intent);
    }
}