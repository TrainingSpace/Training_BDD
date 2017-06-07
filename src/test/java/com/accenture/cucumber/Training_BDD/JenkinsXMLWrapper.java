package com.accenture.cucumber.Training_BDD;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class JenkinsXMLWrapper {

    public static class XMLRoot {
        List<JenkinsJob> jobs = new LinkedList<>();
        Element something;

        XMLRoot(String target, String sourceURL) {
            try {
                URL rootURL = new URL(sourceURL);
                Document dom =  new SAXReader().read(rootURL);

                switch (target){
                    case "job":
                        for( Element job : (List<Element>)dom.getRootElement().elements("job")) {
                            JenkinsJob jenkinsJob = new JenkinsJob(job.elementText("name"), job.elementText("url"), job.elementText("name"),"","");
                            jobs.add(jenkinsJob);
                        }
                        break;

                    case "lastBuild":
                        something = dom.getRootElement();
                        System.out.println("duration = " + something.node(9).getText());
                        System.out.println("\nresult = " + something.node(16).getText());
                        break;
                }

            } catch (MalformedURLException | DocumentException e) {
                e.printStackTrace();
            }
        }


        public class JenkinsJob {
            String name;
            String url;
            String color;
            String duration;
            String result;
            JenkinsJob(String jobName, String jobURL, String jobColor, String jobDuration, String jobResult) {
                name = jobName;
                url = jobURL;
                color = jobColor;
                duration = jobDuration;
                result = jobResult;
            }
        }

        public void ListAllJobs() {
            for (int i = 0; i < jobs.size(); i++) {
                System.out.println(String.format("Name: %s\tURL: %s\tStatus: %s", jobs.get(i).name, jobs.get(i).url, jobs.get(i).color));
            }
        }

    }




    public static void main(String[] args) throws Exception {
        // every Hudson model object exposes the .../api/xml, but in this example
        // we'll just take the root object as an example
        XMLRoot root;
        root = new XMLRoot("lastBuild","http://jenkins-tcoe-qa.disney.com/job/Selenium_ALM_Sync/lastBuild/api/xml");
        // scan through the job list and print its data
       // root.ListAllJobs();

    }
}

