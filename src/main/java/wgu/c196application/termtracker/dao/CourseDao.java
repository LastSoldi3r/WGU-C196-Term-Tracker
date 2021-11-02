package wgu.c196application.termtracker.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import wgu.c196application.termtracker.model.AssessmentEntity;
import wgu.c196application.termtracker.model.CourseEntity;

@Dao
public interface CourseDao {

    @Insert
    void insert(CourseEntity course);

    @Update
    void update(CourseEntity course);

    @Delete
    void delete(CourseEntity course);

    @Query("DELETE FROM course_table")
    void deleteAllCourses();

    @Query("SELECT * FROM course_table ORDER BY courseID ASC")
    List<CourseEntity>getAllCourses();

}
