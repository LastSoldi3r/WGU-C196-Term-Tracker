package wgu.c196application.termtracker.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import wgu.c196application.termtracker.R;
import wgu.c196application.termtracker.database.Repository;
import wgu.c196application.termtracker.model.CourseEntity;
import wgu.c196application.termtracker.model.TermEntity;

public class CourseActivity extends AppCompatActivity {
    private int termID;
    String title;
    String startDate;
    String endDate;
    EditText editTitle;
    EditText editStartDate;
    EditText editEndDate;
    Repository repository;
    List<TermEntity> allTerms;
    List<CourseEntity> allCourses;
    List<CourseEntity> filteredCourses;
    TermEntity currentTerm;
    int numberOfCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        // Creates Navigation Bar with a Back Button
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Create repository instance
        repository = new Repository(getApplication());
        allTerms = repository.getAllTerms();
        allCourses = repository.getAllCourses();

        // Get current Term items to populate EditTexts
        termID = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        startDate = getIntent().getStringExtra("start_date");
        endDate = getIntent().getStringExtra("end_date");
        editTitle = findViewById(R.id.termTitle);
        editTitle.setText(title);
        editStartDate = findViewById(R.id.termStart);
        editStartDate.setText(startDate);
        editEndDate = findViewById(R.id.termEnd);
        editEndDate.setText(endDate);

        // Set currentTerm
        for (TermEntity term : allTerms) {
            if (term.getTermID() == termID) {
                currentTerm = term;
            }
        }
        // Find Courses that belong to the current term
        filteredCourses = new ArrayList<>();
        for (CourseEntity course : allCourses) {
            if (course.getTermID() == termID) {
                filteredCourses.add(course);
            }
        }

        // Set numberOfCourses
        numberOfCourses = filteredCourses.size();

        // Populate the Course RecyclerView
        RecyclerView recyclerView = findViewById(R.id.coursesRecyclerView);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter.setCourses(filteredCourses);

    }

    // Determines what action to take when an option is selected on the navigation bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.homeFromCourses:
                Intent goHome = new Intent(CourseActivity.this, TermActivity.class);
                startActivity(goHome);
                return true;
            case R.id.refreshCourses:
                repository = new Repository(getApplication());
                filteredCourses = new ArrayList<>();
                for (CourseEntity course : allCourses) {
                    if (course.getTermID() == termID) {
                        filteredCourses.add(course);
                    }
                }
                final CourseAdapter courseAdapter = new CourseAdapter(this);
                RecyclerView recyclerView = findViewById(R.id.coursesRecyclerView);
                recyclerView.setAdapter(courseAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                courseAdapter.setCourses(filteredCourses);
                Toast.makeText(CourseActivity.this, "Courses Table refreshed.", Toast.LENGTH_LONG).show();
                return true;
            case R.id.deleteTerm:
                if (numberOfCourses == 0) {
                    repository.delete(currentTerm);
                    Toast.makeText(CourseActivity.this, "Term deleted.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(CourseActivity.this, TermActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(CourseActivity.this, "A Term with Courses can not be deleted.", Toast.LENGTH_LONG).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Inflates he navigation menu with action items
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course, menu);
        return true;
    }

    // Opens a fresh Course Details activity when floating action button is clicked
    public void createNewCourse(View view) {
        if (termID != -1) {
            Intent intent = new Intent(CourseActivity.this, AssessmentActivity.class);
            intent.putExtra("term_ID", currentTerm.getTermID());
            startActivity(intent);
        } else {
            Toast.makeText(CourseActivity.this, "Courses can only be added to an existing Term.", Toast.LENGTH_LONG).show();
        }
    }

    public void saveTerm(View view) {
        String screenTitle = editTitle.getText().toString();
        String screenStartDate = editStartDate.getText().toString();
        String screenEndDate = editEndDate.getText().toString();
        if (termID == -1) {
            termID = allTerms.size();
            TermEntity newTerm = new TermEntity(++termID, screenTitle, screenStartDate, screenEndDate);
            repository.insert(newTerm);
            Toast.makeText(CourseActivity.this, "New Term saved.", Toast.LENGTH_LONG).show();
        } else {
            TermEntity existingTerm = new TermEntity(termID, screenTitle, screenStartDate, screenEndDate);
            repository.update(existingTerm);
            Toast.makeText(CourseActivity.this, "Term updated.", Toast.LENGTH_LONG).show();
        }
        Intent intent = new Intent(CourseActivity.this, TermActivity.class);
        startActivity(intent);
    }
}