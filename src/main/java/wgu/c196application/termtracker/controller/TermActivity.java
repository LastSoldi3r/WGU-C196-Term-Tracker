package wgu.c196application.termtracker.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

import wgu.c196application.termtracker.R;
import wgu.c196application.termtracker.database.Repository;
import wgu.c196application.termtracker.model.TermEntity;

public class TermActivity extends AppCompatActivity {

    private Repository repository;
    List<TermEntity> allTerms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);

        // Creates Navigation Bar with a Back Button
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Create repository instance
        repository = new Repository(getApplication());
        allTerms = repository.getAllTerms();

        // Populate the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.termsRecyclerView);
        final TermAdapter termAdapter = new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setTerms(allTerms);
    }

    // Determines what action to take when an option is selected on the navigation bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.refreshTerms:
                repository = new Repository(getApplication());
                List<TermEntity> allTerms = repository.getAllTerms();
                final TermAdapter termAdapter = new TermAdapter(this);
                RecyclerView recyclerView = findViewById(R.id.termsRecyclerView);
                recyclerView.setAdapter(termAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                termAdapter.setTerms(allTerms);
                Toast.makeText(TermActivity.this, "Terms Table refreshed.", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    // Inflates he navigation menu with action items
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_term, menu);
        return true;
    }

    // Opens a fresh Term Details activity when floating action button is clicked
    public void createNewTerm(View view) {
        Intent intent = new Intent(TermActivity.this, CourseActivity.class);
        startActivity(intent);
    }
}