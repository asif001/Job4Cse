package cse.job.asif.job4cse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class HtmlParse {

    private  ArrayList<JobDetails> JobList;
    private String baseUrl = "";
    private String origin = "";

    private Document DownloadPage(String pageNo) throws IOException{

        String possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String random = "";
        for(Integer i = 0;i < 6;i++){

            random = random + possible.charAt((int)Math.floor(Math.random() * possible.length()));

        }

        String tmpUrl = baseUrl + "&var=" + random;

        Document document = Jsoup.connect(tmpUrl).data("pg",pageNo).get();

        return document;

    }

    private String getJobUrl(Element JobElement){

        Elements query = JobElement.getElementsByClass("job-title-text");

        if(!query.isEmpty())
            return this.origin + query.first().getElementsByTag("a").first().attr("href");
        else
            return "";

    }

    private String getLogo(Element JobElement){

        Elements query = JobElement.getElementsByClass("comp_logo");

        if(!query.isEmpty())
            return query.first().getElementsByTag("img").first().attr("src");
        else
            return "";

    }

    private String getTitle(Element JobElement){

        Elements query = JobElement.getElementsByClass("job-title-text");

        if(!query.isEmpty())
            return query.first().text();
        else
            return "";

    }

    private  String getCompName(Element JobElement){

        Elements query = JobElement.getElementsByClass("comp-name-text");

        if(!query.isEmpty())
            return query.first().text();
        else
            return "";

    }

    private String getLocation(Element JobElement){

        Elements query = JobElement.getElementsByClass("locon-text-d");

        if(!query.isEmpty())
            return query.first().text();
        else
            return "";

    }

    private ArrayList<String> getQualifications(Element JobElement){

        ArrayList<String> Qualifications = new ArrayList<>();

        Elements query = JobElement.getElementsByClass("edu-text-d");

        if(!query.isEmpty()){

            Elements qualListElement = query.first().getElementsByTag("li");

            if(!qualListElement.isEmpty()){

                for(Element qualList : qualListElement){

                    Qualifications.add(qualList.text());

                }

            }

            return Qualifications;

        }

        else return new ArrayList<>();

    }

    private String getExp(Element JobElement){

        Elements query = JobElement.getElementsByClass("exp-text-d");

        if(!query.isEmpty())
            return query.first().text();
        else
            return "";

    }

    private String getDeadline(Element JobElement){

        Elements query = JobElement.getElementsByClass("dead-text-d");

        if(!query.isEmpty())
            return query.first().text();
        else
            return "";

    }

    private void addToList(Elements JobElementsCat){

        if(!JobElementsCat.isEmpty()) {

            for (Element JobElement : JobElementsCat) {

                JobDetails jobDetails = new JobDetails();

                jobDetails.setJobUrl(getJobUrl(JobElement));
                jobDetails.setLogo(getLogo(JobElement));
                jobDetails.setTitle(getTitle(JobElement));
                jobDetails.setCompname(getCompName(JobElement));
                jobDetails.setLocation(getLocation(JobElement));
                jobDetails.setQualifications(getQualifications(JobElement));
                jobDetails.setExp(getExp(JobElement));
                jobDetails.setDeadline(getDeadline(JobElement));

                JobList.add(jobDetails);

            }

        }

    }

    public  ArrayList<JobDetails> Start(String origin, String url,String page,ArrayList<JobDetails> JobList) throws IOException {


        this.baseUrl = url;
        this.origin = origin;
        this.JobList = JobList;

        Document document = DownloadPage(page);

        Elements JobElementsCat1 = document.getElementsByClass("sout-jobs-wrapper");
        Elements JobElementsCat2 = document.getElementsByClass("norm-jobs-wrapper");

        addToList(JobElementsCat1);
        addToList(JobElementsCat2);

        return JobList;

    }

}

