package wgu.c196application.termtracker.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "term_table")
public class TermEntity {

    /*
        ----------VARIABLES----------
    */

    @PrimaryKey(autoGenerate = true)
    private int termID;

    @ColumnInfo(name = "title")
    private String termTitle;
    @ColumnInfo(name = "start_Date")
    private String termStartDate;
    @ColumnInfo(name = "end_Date")
    private String termEndDate;

    /*
        ----------CONSTRUCTORS----------
    */

    public TermEntity(int termID, String termTitle, String termStartDate, String termEndDate) {
        this.termID = termID;
        this.termTitle = termTitle;
        this.termStartDate = termStartDate;
        this.termEndDate = termEndDate;
    }

    /*
        ----------SETTERS----------
    */

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public void setTermTitle(String termTitle) {
        this.termTitle = termTitle;
    }

    public void setTermStartDate(String termStartDate) {
        this.termStartDate = termStartDate;
    }

    public void setTermEndDate(String termEndDate) {
        this.termEndDate = termEndDate;
    }

    /*
        ----------GETTERS----------
    */

    public int getTermID() {
        return termID;
    }

    public String getTermTitle() {
        return termTitle;
    }

    public String getTermStartDate() {
        return termStartDate;
    }

    public String getTermEndDate() {
        return termEndDate;
    }

    /*
        ----------METHODS----------
    */

    @Override
    public String toString() {
        return "TermEntity{" +
                "termTitle='" + termTitle + '\'' +
                ", termStartDate='" + termStartDate + '\'' +
                ", termEndDate='" + termEndDate + '\'' +
                '}';
    }
}
