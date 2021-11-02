package wgu.c196application.termtracker.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import wgu.c196application.termtracker.dao.TermDao;
import wgu.c196application.termtracker.model.TermEntity;

@Database(entities = TermEntity.class, version = 1)
public abstract class TermDatabase extends RoomDatabase {

    private static TermDatabase INSTANCE;

    public abstract TermDao termDao();

    public static synchronized TermDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (TermDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TermDatabase.class, "term_Database.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
