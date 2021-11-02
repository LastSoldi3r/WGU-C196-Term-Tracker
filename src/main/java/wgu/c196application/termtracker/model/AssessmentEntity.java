package wgu.c196application.termtracker.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "assessment_table")
public class AssessmentEntity {

    /*
        ----------VARIABLES----------
    */

    @PrimaryKey(autoGenerate = true)
    private int assessmentID;

    @ColumnInfo(name = "title")
    private String assessmentTitle;
    @ColumnInfo(name = "start_Date")
    private String assessmentStartDate;
    @ColumnInfo(name = "end_Date")
    private String assessmentEndDate;
    @ColumnInfo(name = "type")
    private String assessmentType;
    private int courseID;

    /*
        ----------CONSTRUCTORS----------
    */

    public AssessmentEntity(int assessmentID, String assessmentTitle, String assessmentStartDate, String assessmentEndDate, String assessmentType, int courseID) {
        this.assessmentID = assessmentID;
        this.assessmentTitle = assessmentTitle;
        this.assessmentStartDate = assessmentStartDate;
        this.assessmentEndDate = assessmentEndDate;
        this.assessmentType = assessmentType;
        this.courseID = courseID;
    }

    /*
        ----------SETTERS----------
    */

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }

    public void setAssessmentStartDate(String assessmentStartDate) {
        this.assessmentStartDate = assessmentStartDate;
    }

    public void setAssessmentEndDate(String assessmentEndDate) {
        this.assessmentEndDate = assessmentEndDate;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    /*
        ----------GETTERS----------
    */

    public int getAssessmentID() {
        return assessmentID;
    }

    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    public String getAssessmentStartDate() {
        return assessmentStartDate;
    }

    public String getAssessmentEndDate() {
        return assessmentEndDate;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public int getCourseID() {
        return courseID;
    }

    /*
        ----------METHODS----------
    */

    @Override
    public String toString() {
        return "AssessmentEntity{" +
                ", assessmentTitle='" + assessmentTitle + '\'' +
                ", assessmentStartDate='" + assessmentStartDate + '\'' +
                ", assessmentEndDate='" + assessmentEndDate + '\'' +
                ", assessmentType='" + assessmentType + '\'' +
                ", courseID=" + courseID +
                '}';
    }
}
