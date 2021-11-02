package wgu.c196application.termtracker.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import wgu.c196application.termtracker.model.AssessmentEntity;
import wgu.c196application.termtracker.model.TermEntity;

@Dao
public interface TermDao {

    @Insert
    void insert(TermEntity term);

    @Update
    void update(TermEntity term);

    @Delete
    void delete(TermEntity term);

    @Query("DELETE FROM term_table")
    void deleteAllTerms();

    @Query("SELECT * FROM term_table ORDER BY termID ASC")
    List<TermEntity> getAllTerms();

}
