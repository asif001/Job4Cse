package cse.job.asif.job4cse;

import java.util.ArrayList;

public class JobDetails {

    private String logo = "";
    private String title = "";
    private String Compname = "";
    private String location = "";
    private ArrayList<String> Qualifications;
    private String Exp = "";
    private String deadline = "";

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

