package wgu.c196application.termtracker.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import wgu.c196application.termtracker.dao.AssessmentDao;
import wgu.c196application.termtracker.model.AssessmentEntity;

@Database(entities = AssessmentEntity.class, version = 1)
public abstract class AssessmentDatabase extends RoomDatabase {

    private static AssessmentDatabase INSTANCE;

    public abstract AssessmentDao assessmentDao();

    public static AssessmentDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AssessmentDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AssessmentDatabase.class, "assessment_Database.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
