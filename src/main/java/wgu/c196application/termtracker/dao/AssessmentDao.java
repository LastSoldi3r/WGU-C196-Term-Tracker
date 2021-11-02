package wgu.c196application.termtracker.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import wgu.c196application.termtracker.model.AssessmentEntity;

@Dao
public interface AssessmentDao {

    @Insert
    void insert(AssessmentEntity Assessment);

    @Update
    void update(AssessmentEntity Assessment);

    @Delete
    void delete(AssessmentEntity Assessment);

    @Query("DELETE FROM assessment_table")
    void deleteAllAssessments();

    @Query("SELECT * FROM assessment_table ORDER BY assessmentID ASC")
    List<AssessmentEntity> getAllAssessments();

}
