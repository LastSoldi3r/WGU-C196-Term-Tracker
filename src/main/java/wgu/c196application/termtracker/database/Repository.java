package wgu.c196application.termtracker.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import wgu.c196application.termtracker.dao.AssessmentDao;
import wgu.c196application.termtracker.dao.CourseDao;
import wgu.c196application.termtracker.dao.TermDao;
import wgu.c196application.termtracker.model.AssessmentEntity;
import wgu.c196application.termtracker.model.CourseEntity;
import wgu.c196application.termtracker.model.TermEntity;

public class Repository {

    /*
        ----------VARIABLES----------
    */

    private AssessmentDao assessmentDao;
    private CourseDao courseDao;
    private TermDao termDao;
    private List<AssessmentEntity> allAssessments;
    private List<CourseEntity> allCourses;
    private List<TermEntity> allTerms;

    private static final int THREADS = 4;
    static ExecutorService databaseExecutor = Executors.newFixedThreadPool(THREADS);

    public Repository(Application application) {
        AssessmentDatabase assessmentDatabase = AssessmentDatabase.getDatabase(application);
        assessmentDao = assessmentDatabase.assessmentDao();

        CourseDatabase courseDatabase = CourseDatabase.getDatabase(application);
        courseDao = courseDatabase.courseDao();

        TermDatabase termDatabase = TermDatabase.getDatabase(application);
        termDao = termDatabase.termDao();
    }

    /*
        ----------ASSESSMENTS----------
    */

    public void insert(AssessmentEntity assessment) {
        databaseExecutor.execute(()->{
            assessmentDao.insert(assessment);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(AssessmentEntity assessment) {
        databaseExecutor.execute(()->{
            assessmentDao.update(assessment);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(AssessmentEntity assessment) {
        databaseExecutor.execute(()->{
            assessmentDao.delete(assessment);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<AssessmentEntity> getAllAssessments() {
        databaseExecutor.execute(()->{
            allAssessments = assessmentDao.getAllAssessments();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return allAssessments;
    }

    /*
        ----------COURSES----------
    */

    public void insert(CourseEntity course) {
        databaseExecutor.execute(()->{
            courseDao.insert(course);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(CourseEntity course) {
        databaseExecutor.execute(()->{
            courseDao.update(course);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(CourseEntity course) {
        databaseExecutor.execute(()->{
            courseDao.delete(course);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<CourseEntity> getAllCourses() {
        databaseExecutor.execute(()->{
            allCourses = courseDao.getAllCourses();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return allCourses;
    }

    /*
        ----------TERMS----------
    */

    public void insert(TermEntity term) {
        databaseExecutor.execute(()->{
            termDao.insert(term);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(TermEntity term) {
        databaseExecutor.execute(()->{
            termDao.update(term);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(TermEntity term) {
        databaseExecutor.execute(()->{
            termDao.delete(term);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<TermEntity> getAllTerms() {
        databaseExecutor.execute(()->{
            allTerms = termDao.getAllTerms();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return allTerms;
    }

}
