package wgu.c196application.termtracker.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import wgu.c196application.termtracker.R;
import wgu.c196application.termtracker.database.Repository;
import wgu.c196application.termtracker.model.AssessmentEntity;
import wgu.c196application.termtracker.model.CourseEntity;
import wgu.c196application.termtracker.model.TermEntity;

public class AssessmentActivity extends AppCompatActivity {
    private int courseID;
    String title;
    String startDate;
    String endDate;
    String status;
    String instructorName;
    String instructorPhone;
    String instructorEmail;
    String note;
    EditText editTitle;
    EditText editStartDate;
    EditText editEndDate;
    EditText editStatus;
    EditText editInstructorName;
    EditText editInstructorPhone;
    EditText editInstructorEmail;
    EditText editNote;
    Repository repository;
    private int termID;
    List<TermEntity> allTerms;
    TermEntity termForCourse;
    List<CourseEntity> allCourses;
    List<AssessmentEntity> allAssessments;
    List<AssessmentEntity> filteredAssessments;
    CourseEntity currentCourse;
    int numberOfAssessments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);

        // Creates Navigation Bar with a Back Button
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Create repository instance
        repository = new Repository(getApplication());
        allTerms = repository.getAllTerms();
        allCourses = repository.getAllCourses();
        allAssessments = repository.getAllAssessments();

        // Get current Course items to populate EditTexts
        courseID = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        startDate = getIntent().getStringExtra("start_date");
        endDate = getIntent().getStringExtra("end_date");
        status = getIntent().getStringExtra("status");
        instructorName = getIntent().getStringExtra("instructor_name");
        instructorPhone = getIntent().getStringExtra("instructor_phone");
        instructorEmail = getIntent().getStringExtra("instructor_email");
        note = getIntent().getStringExtra("note");
        termID = getIntent().getIntExtra("term_ID", -1);
        editTitle = findViewById(R.id.courseTitle);
        editTitle.setText(title);
        editStartDate = findViewById(R.id.courseStart);
        editStartDate.setText(startDate);
        editEndDate = findViewById(R.id.courseEnd);
        editEndDate.setText(endDate);
        editStatus = findViewById(R.id.courseStatus);
        editStatus.setText(status);
        editInstructorName = findViewById(R.id.courseInstructorName);
        editInstructorName.setText(instructorName);
        editInstructorPhone = findViewById(R.id.courseInstructorPhone);
        editInstructorPhone.setText(instructorPhone);
        editInstructorEmail = findViewById(R.id.courseInstructorEmail);
        editInstructorEmail.setText(instructorEmail);
        editNote = findViewById(R.id.courseNote);
        editNote.setText(note);

        // Set currentCourse
        for (CourseEntity course : allCourses) {
            if (course.getCourseID() == courseID) {
                currentCourse = course;
            }
        }
        // Find Assessments that belong to the current course
        filteredAssessments = new ArrayList<>();
        for (AssessmentEntity assessment : allAssessments) {
            if (assessment.getCourseID() == courseID) {
                filteredAssessments.add(assessment);
            }
        }

        // Set numberOfAssessments
        numberOfAssessments = filteredAssessments.size();

        // Populate the Assessment RecyclerView
        RecyclerView recyclerView = findViewById(R.id.assessmentsRecyclerView);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        assessmentAdapter.setAssessments(filteredAssessments);
    }

    // Determines what action to take when an option is selected on the navigation bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.homeFromAssessments:
                Intent goHome = new Intent(AssessmentActivity.this, TermActivity.class);
                startActivity(goHome);
                return true;
            case R.id.refreshAssessments:
                repository = new Repository(getApplication());
                filteredAssessments = new ArrayList<>();
                for (AssessmentEntity assessment : allAssessments) {
                    if (assessment.getCourseID() == courseID) {
                        filteredAssessments.add(assessment);
                    }
                }
                final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
                RecyclerView recyclerView = findViewById(R.id.assessmentsRecyclerView);
                recyclerView.setAdapter(assessmentAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                assessmentAdapter.setAssessments(filteredAssessments);
                Toast.makeText(AssessmentActivity.this, "Assessments Table refreshed.", Toast.LENGTH_LONG).show();
                return true;
            case R.id.deleteCourse:
                if (numberOfAssessments == 0) {
                    repository.delete(currentCourse);
                    Toast.makeText(AssessmentActivity.this, "Course deleted.", Toast.LENGTH_LONG).show();
                    for (TermEntity term : allTerms) {
                        termForCourse = term;
                    }
                    Intent intent = new Intent(AssessmentActivity.this, CourseActivity.class);
                    intent.putExtra("id", termForCourse.getTermID());
                    intent.putExtra("title", termForCourse.getTermTitle());
                    intent.putExtra("start_date", termForCourse.getTermStartDate());
                    intent.putExtra("end_date", termForCourse.getTermEndDate());
                    startActivity(intent);
                } else {
                    Toast.makeText(AssessmentActivity.this, "A Course with Assessments can not be deleted.", Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.shareCourseNotes:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, editNote.getText().toString());
                sendIntent.putExtra(Intent.EXTRA_TITLE, editTitle.getText().toString() + " Notes" );
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;
            case R.id.notifyCourseStart:
                String screenStartDate = editStartDate.getText().toString();
                String dateFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
                Date date = null;
                try {
                    date = sdf.parse(screenStartDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long trigger = date.getTime();
                Intent notifyStart = new Intent(AssessmentActivity.this, MyReceiver.class);
                notifyStart.putExtra("key", "Your " + title + " course STARTS " + startDate + "!");
                PendingIntent pendingStart = PendingIntent.getBroadcast(AssessmentActivity.this, ++MainActivity.numAlert, notifyStart, 0);
                AlarmManager startAlarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                startAlarm.set(AlarmManager.RTC_WAKEUP, trigger, pendingStart);
                return true;
            case R.id.notifyCourseEnd:
                String screenEndDate = editEndDate.getText().toString();
                dateFormat = "MM/dd/yy";
                sdf = new SimpleDateFormat(dateFormat, Locale.US);
                date = null;
                try {
                    date = sdf.parse(screenEndDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                trigger = date.getTime();
                Intent notifyEnd = new Intent(AssessmentActivity.this, MyReceiver.class);
                notifyEnd.putExtra("key", "Your " + title + " course ENDS " + endDate + "!");
                PendingIntent pendingEnd = PendingIntent.getBroadcast(AssessmentActivity.this, ++MainActivity.numAlert, notifyEnd, 0);
                AlarmManager endAlarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                endAlarm.set(AlarmManager.RTC_WAKEUP, trigger, pendingEnd);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Inflates he navigation menu with action items
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessment, menu);
        return true;
    }

    // Opens a fresh Assessment Details activity when floating action button is clicked
    public void createNewAssessment(View view) {
        if (courseID != -1) {
            Intent intent = new Intent(AssessmentActivity.this, AssessmentDetailsActivity.class);
            intent.putExtra("course_ID", currentCourse.getCourseID());
            startActivity(intent);
        } else {
            Toast.makeText(AssessmentActivity.this, "Assessments can only be added to an existing Course.", Toast.LENGTH_LONG).show();
        }
    }

    public void saveCourse(View view) {
        String screenTitle = editTitle.getText().toString();
        String screenStartDate = editStartDate.getText().toString();
        String screenEndDate = editEndDate.getText().toString();
        String screenStatus = editStatus.getText().toString();
        String screenInstructorName = editInstructorName.getText().toString();
        String screenInstructorPhone = editInstructorPhone.getText().toString();
        String screenInstructorEmail = editInstructorEmail.getText().toString();
        String screenNote = editNote.getText().toString();
        if (termID == -1) {
            Toast.makeText(AssessmentActivity.this, "A Course must be added to an existing Term.", Toast.LENGTH_LONG).show();
        } else {
            if (courseID == -1) {
                courseID = allCourses.size();
                CourseEntity newCourse = new CourseEntity(++courseID, screenTitle, screenStartDate, screenEndDate,
                        screenStatus, screenInstructorName, screenInstructorPhone, screenInstructorEmail, screenNote,
                        termID);
                repository.insert(newCourse);
                Toast.makeText(AssessmentActivity.this, "New Course saved.", Toast.LENGTH_LONG).show();
            } else {
                CourseEntity existingCourse = new CourseEntity(courseID, screenTitle, screenStartDate, screenEndDate,
                        screenStatus, screenInstructorName, screenInstructorPhone, screenInstructorEmail, screenNote,
                        termID);
                repository.update(existingCourse);
                Toast.makeText(AssessmentActivity.this, "Course updated.", Toast.LENGTH_LONG).show();
            }
        }
        for (TermEntity term : allTerms) {
            if (term.getTermID() == termID) {
                termForCourse = term;
            }
        }
        Intent intent = new Intent(AssessmentActivity.this, CourseActivity.class);
        intent.putExtra("id", termForCourse.getTermID());
        intent.putExtra("title", termForCourse.getTermTitle());
        intent.putExtra("start_date", termForCourse.getTermStartDate());
        intent.putExtra("end_date", termForCourse.getTermEndDate());
        startActivity(intent);
    }
}