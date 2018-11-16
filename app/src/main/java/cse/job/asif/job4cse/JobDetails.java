package cse.job.asif.job4cse;

import java.util.ArrayList;

public class JobDetails {

    private int id;
    private String logo = "";
    private String title = "";
    private String Compname = "";
    private String location = "";
    private ArrayList<String> Qualifications;
    private String Exp = "";
    private String deadline = "";

    public static final String TABLE_NAME = "jobs";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_COMPNAME = "comp_name";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_QUALIFIACTIONS = "qualifications";
    public static final String COLUMN_EXP = "exp";
    public static final String COLUMN_DEAD = "deadline";
    public static final String COLUMN_URL = "url";
    public static final String COLUMN_LOGO = "logo";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_LOGO + " TEXT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_COMPNAME + " TEXT,"
                + COLUMN_LOCATION + " TEXT,"
                + COLUMN_QUALIFIACTIONS + " TEXT,"
                + COLUMN_URL + " TEXT,"
                + COLUMN_EXP + " TEXT,"
                + COLUMN_DEAD + " TEXT,"
                + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
                + "UNIQUE(" + COLUMN_URL + ")"
                + ")";


    public String getJobUrl() {
        return jobUrl;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setJobUrl(String jobUrl) {
        this.jobUrl = jobUrl;
    }

    private String jobUrl = "";

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompname() {
        return Compname;
    }

    public void setCompname(String compname) {
        Compname = compname;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ArrayList<String> getQualifications() {
        return Qualifications;
    }

    public void setQualifications(ArrayList<String> qualifications) {
        Qualifications = qualifications;
    }

    public String getExp() {
        return Exp;
    }

    public void setExp(String exp) {
        Exp = exp;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

}

