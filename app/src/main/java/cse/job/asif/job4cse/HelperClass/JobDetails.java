package cse.job.asif.job4cse.HelperClass;

import java.util.ArrayList;

public class JobDetails {

    private int id;
    private String logo = "";
    private String title = "";
    private String Compname = "";
    private String location = "";
    private ArrayList<String> Qualifications;
    private String Exp = "";
    private String notify = "";
    private String deadline = "";

    public String getJobUrl() {
        return jobUrl;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setNotify(String notify){
        this.notify = notify;
    }

    public String getNotify(){
        return notify;
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

