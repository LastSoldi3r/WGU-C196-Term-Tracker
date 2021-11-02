package wgu.c196application.termtracker.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "course_table")
public class CourseEntity {

    /*
        ----------VARIABLES----------
    */

    @PrimaryKey(autoGenerate = true)
    private int courseID;

    @ColumnInfo(name = "title")
    private String courseTitle;
    @ColumnInfo(name = "start_Date")
    private String courseStartDate;
    @ColumnInfo(name = "end_Date")
    private String courseEndDate;
    @ColumnInfo(name = "status")
    private String courseStatus;
    @ColumnInfo(name = "instructor_Name")
    private String courseInstructorName;
    @ColumnInfo(name = "instructor_Phone")
    private String courseInstructorPhone;
    @ColumnInfo(name = "instructor_Email")
    private String courseInstructorEmail;
    @ColumnInfo(name = "note")
    private String courseNote;
    private int termID;

    /*
        ----------CONSTRUCTORS----------
    */

    public CourseEntity(int courseID, String courseTitle, String courseStartDate, String courseEndDate, String courseStatus,
                        String courseInstructorName, String courseInstructorPhone, String courseInstructorEmail, String courseNote, int termID) {
        this.courseID = courseID;
        this.courseTitle = courseTitle;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.courseStatus = courseStatus;
        this.courseInstructorName = courseInstructorName;
        this.courseInstructorPhone = courseInstructorPhone;
        this.courseInstructorEmail = courseInstructorEmail;
        this.courseNote = courseNote;
        this.termID = termID;
    }

    /*
        ----------SETTERS----------
    */

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public void setCourseStartDate(String courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public void setCourseEndDate(String courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public void setCourseInstructorName(String courseInstructorName) {
        this.courseInstructorName = courseInstructorName;
    }

    public void setCourseInstructorPhone(String courseInstructorPhone) {
        this.courseInstructorPhone = courseInstructorPhone;
    }

    public void setCourseInstructorEmail(String courseInstructorEmail) {
        this.courseInstructorEmail = courseInstructorEmail;
    }

    public void setCourseNote(String courseNote) {
        this.courseNote = courseNote;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    /*
        ----------GETTERS----------
    */

    public int getCourseID() {
        return courseID;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getCourseStartDate() {
        return courseStartDate;
    }

    public String getCourseEndDate() {
        return courseEndDate;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public String getCourseInstructorName() {
        return courseInstructorName;
    }

    public String getCourseInstructorPhone() {
        return courseInstructorPhone;
    }

    public String getCourseInstructorEmail() {
        return courseInstructorEmail;
    }

    public String getCourseNote() {
        return courseNote;
    }

    public int getTermID() {
        return termID;
    }

    /*
        ----------METHODS----------
    */

    @Override
    public String toString() {
        return "CourseEntity{" +
                "courseID=" + courseID +
                ", courseTitle='" + courseTitle + '\'' +
                ", courseStartDate='" + courseStartDate + '\'' +
                ", courseEndDate='" + courseEndDate + '\'' +
                ", courseStatus='" + courseStatus + '\'' +
                ", courseInstructorName='" + courseInstructorName + '\'' +
                ", courseInstructorPhone='" + courseInstructorPhone + '\'' +
                ", courseInstructorEmail='" + courseInstructorEmail + '\'' +
                ", courseNote='" + courseNote + '\'' +
                ", termID=" + termID +
                '}';
    }
}
