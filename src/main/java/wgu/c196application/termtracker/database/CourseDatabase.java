package wgu.c196application.termtracker.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import wgu.c196application.termtracker.dao.CourseDao;
import wgu.c196application.termtracker.model.CourseEntity;

@Database(entities = CourseEntity.class, version = 1)
public abstract class CourseDatabase extends RoomDatabase {

    private static CourseDatabase INSTANCE;

    public abstract CourseDao courseDao();

    public static CourseDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (CourseDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CourseDatabase.class, "course_Database.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
