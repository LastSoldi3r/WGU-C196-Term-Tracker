package wgu.c196application.termtracker.controller;

import androidx.appcompat.app.AppCompatActivity;

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
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import wgu.c196application.termtracker.R;
import wgu.c196application.termtracker.database.Repository;
import wgu.c196application.termtracker.model.AssessmentEntity;
import wgu.c196application.termtracker.model.CourseEntity;

public class AssessmentDetailsActivity extends AppCompatActivity {
    private int assessmentID;
    String title;
    String startDate;
    String endDate;
    String type;
    private int courseID;
    EditText editTitle;
    EditText editStartDate;
    EditText editEndDate;
    EditText editType;
    Repository repository;
    List<CourseEntity> allCourses;
    CourseEntity courseForAssessment;
    List<AssessmentEntity> allAssessments;
    AssessmentEntity currentAssessment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);

        // Creates Navigation Bar with a Back Button
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Create repository instance
        repository = new Repository(getApplication());
        allCourses = repository.getAllCourses();
        allAssessments = repository.getAllAssessments();

        // Get current Assessment items to populate EditTexts
        assessmentID = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        startDate = getIntent().getStringExtra("start_date");
        endDate = getIntent().getStringExtra("end_date");
        type = getIntent().getStringExtra("type");
        courseID = getIntent().getIntExtra("course_ID", -1);
        editTitle = findViewById(R.id.assessmentTitle);
        editTitle.setText(title);
        editStartDate = findViewById(R.id.assessmentStart);
        editStartDate.setText(startDate);
        editEndDate = findViewById(R.id.assessmentEnd);
        editEndDate.setText(endDate);
        editType = findViewById(R.id.assessmentType);
        editType.setText(type);

        // Set currentAssessment
        for (AssessmentEntity assessment : allAssessments) {
            if (assessment.getAssessmentID() == assessmentID) {
                currentAssessment = assessment;
            }
        }
    }

    // Determines what action to take when an option is selected on the navigation bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.deleteAssessment:
                repository.delete(currentAssessment);
                Toast.makeText(AssessmentDetailsActivity.this, "Assessment deleted.", Toast.LENGTH_LONG).show();
                for (CourseEntity course : allCourses) {
                    if (course.getCourseID() == courseID) {
                        courseForAssessment = course;
                    }
                }
                Intent intent = new Intent(AssessmentDetailsActivity.this, AssessmentActivity.class);
                intent.putExtra("id", courseForAssessment.getCourseID());
                intent.putExtra("title", courseForAssessment.getCourseTitle());
                intent.putExtra("start_date", courseForAssessment.getCourseStartDate());
                intent.putExtra("end_date", courseForAssessment.getCourseEndDate());
                intent.putExtra("status", courseForAssessment.getCourseStatus());
                intent.putExtra("instructor_name", courseForAssessment.getCourseInstructorName());
                intent.putExtra("instructor_phone", courseForAssessment.getCourseInstructorPhone());
                intent.putExtra("instructor_email", courseForAssessment.getCourseInstructorEmail());
                intent.putExtra("note", courseForAssessment.getCourseNote());
                intent.putExtra("term_ID", courseForAssessment.getTermID());
                startActivity(intent);
            case R.id.notifyAssessmentStart:
                String screenAssessmentStart = editStartDate.getText().toString();
                String dateFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
                Date date = null;
                try {
                    date = sdf.parse(screenAssessmentStart);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long trigger = date.getTime();
                Intent notifyAssessmentStart = new Intent(AssessmentDetailsActivity.this, MyReceiver.class);
                notifyAssessmentStart.putExtra("key", "Your " + title + " assessment STARTS " + startDate + "!");
                PendingIntent pendingAssessmentStart = PendingIntent.getBroadcast(AssessmentDetailsActivity.this, ++MainActivity.numAlert, notifyAssessmentStart, 0);
                AlarmManager startAlarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                startAlarm.set(AlarmManager.RTC_WAKEUP, trigger, pendingAssessmentStart);
                return true;
            case R.id.notifyAssessmentEnd:
                String screenAssessmentEnd = editEndDate.getText().toString();
                dateFormat = "MM/dd/yy";
                sdf = new SimpleDateFormat(dateFormat, Locale.US);
                date = null;
                try {
                    date = sdf.parse(screenAssessmentEnd);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                trigger = date.getTime();
                Intent notifyAssessmentEnd = new Intent(AssessmentDetailsActivity.this, MyReceiver.class);
                notifyAssessmentEnd.putExtra("key", "Your " + title + " assessment ENDS " + endDate + "!");
                PendingIntent pendingAssessmentEnd = PendingIntent.getBroadcast(AssessmentDetailsActivity.this, ++MainActivity.numAlert, notifyAssessmentEnd, 0);
                AlarmManager endAlarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                endAlarm.set(AlarmManager.RTC_WAKEUP, trigger, pendingAssessmentEnd);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Inflates he navigation menu with action items
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessment_details, menu);
        return true;
    }

    public void saveAssessment(View view) {
        String screenTitle = editTitle.getText().toString();
        String screenStartDate = editStartDate.getText().toString();
        String screenEndDate = editEndDate.getText().toString();
        String screenType = editType.getText().toString();
        if (courseID == -1) {
            Toast.makeText(AssessmentDetailsActivity.this, "An Assessment must be added to an existing Course.", Toast.LENGTH_LONG).show();
        } else {
            if (assessmentID == -1) {
                assessmentID = allAssessments.size();
                AssessmentEntity newAssessment = new AssessmentEntity(++assessmentID, screenTitle, screenStartDate, screenEndDate,
                        screenType, courseID);
                repository.insert(newAssessment);
                Toast.makeText(AssessmentDetailsActivity.this, "New Assessment saved.", Toast.LENGTH_LONG).show();
            } else  {
                AssessmentEntity existingAssessment = new AssessmentEntity(assessmentID, screenTitle, screenStartDate, screenEndDate,
                        screenType, courseID);
                repository.update(existingAssessment);
                Toast.makeText(AssessmentDetailsActivity.this, "Assessment updated.", Toast.LENGTH_LONG).show();
            }
        }
        for (CourseEntity course : allCourses) {
            if (course.getCourseID() == courseID) {
                courseForAssessment = course;
            }
        }
        Intent intent = new Intent(AssessmentDetailsActivity.this, AssessmentActivity.class);
        intent.putExtra("id", courseForAssessment.getCourseID());
        intent.putExtra("title", courseForAssessment.getCourseTitle());
        intent.putExtra("start_date", courseForAssessment.getCourseStartDate());
        intent.putExtra("end_date", courseForAssessment.getCourseEndDate());
        intent.putExtra("status", courseForAssessment.getCourseStatus());
        intent.putExtra("instructor_name", courseForAssessment.getCourseInstructorName());
        intent.putExtra("instructor_phone", courseForAssessment.getCourseInstructorPhone());
        intent.putExtra("instructor_email", courseForAssessment.getCourseInstructorEmail());
        intent.putExtra("note", courseForAssessment.getCourseNote());
        intent.putExtra("term_ID", courseForAssessment.getTermID());
        startActivity(intent);
    }
}