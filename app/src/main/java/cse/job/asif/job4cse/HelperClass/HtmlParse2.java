package cse.job.asif.job4cse.HelperClass;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class HtmlParse2 {

    private ArrayList<JobDetails> JobList;
    private String baseUrl = "";
    private String origin = "";

    private Document DownloadPage(String pageNo) throws IOException {


        String tmpUrl = baseUrl + "&page=" + pageNo;

        Document document = Jsoup.connect(tmpUrl).userAgent("Mozilla").timeout(200000).get();

        return document;

    }

    private String getJobUrl(Element JobElement){

        Elements query = JobElement.getElementsByClass("link02");

        if(!query.isEmpty())
            return this.origin + query.first().attr("href");
        else
            return "";

    }

    private String getLogo(Element JobElement){

            return "";

    }

    private String getTitle(Element JobElement){

        Elements query = JobElement.getElementsByClass("link02");

        if(!query.isEmpty())
            return query.first().text();
        else
            return "";

    }

    private  String getCompName(Element JobElement){

        Elements query = JobElement.getElementsByClass("HotJobsCompany");

        if(!query.isEmpty())
            return query.first().text();
        else
            return "";

    }

    private String getLocation(Element JobElement){

            return "";

    }

    private ArrayList<String> getQualifications(Element JobElement){

        ArrayList<String> Qualifications = new ArrayList<>();

        Elements query = JobElement.getElementsByClass("detailsnoncap");

        if(!query.isEmpty()){

            Qualifications.add(query.first().text());

            return Qualifications;

        }

        else return new ArrayList<>();

    }

    private String getExp(Element JobElement){

        Elements query = JobElement.getElementsByClass("detailsnoncap");

        if(!query.isEmpty())
            return query.get(1).text();
        else
            return "";

    }

    private String getDeadline(Element JobElement){

        Elements query = JobElement.getElementsByClass("details");

        if(!query.isEmpty())
            return query.get(1).getElementsByTag("strong").first().text();
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
                jobDetails.setId(Sequence.nextValue());

                JobList.add(jobDetails);

            }

        }

    }

    public  ArrayList<JobDetails> Start(String origin, String url,String page,ArrayList<JobDetails> JobList) throws IOException {


        this.baseUrl = url;
        this.origin = origin;
        this.JobList = JobList;

        Document document = DownloadPage(page);

        Elements JobElementsCat1 = document.getElementsByClass("list");

        addToList(JobElementsCat1);

        return JobList;

    }


}
