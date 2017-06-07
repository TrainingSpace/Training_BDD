package com.accenture.cucumber.Training_BDD;

/**
 * Created by fernanda.menks on 6/7/2017.
 */
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JenkinsCLIWrapper {

    public static void main(String args[]) throws IOException, ParseException
    {
        //String sURL = "http://fefezinha.com:8080/jenkins/job/Training_BDD/lastBuild/api/json";
        String sURL = "http://jenkins-tcoe-qa.disney.com/job/Selenium_ALM_Sync/lastBuild/api/json";

        //parse the URL to JSONObject
        JSONObject lastBuild = jsonParse(sURL);

        //Reading the String
        String buildResult = (String) lastBuild.get("result");
        Long duration = (Long) lastBuild.get("duration");

        //Printing all the values
        System.out.println("Result: " + buildResult);
        System.out.println("Duration: " + duration);
    }

    public static JSONObject jsonParse(String jsonURL) throws IOException, ParseException
    {
        URL url = new URL(jsonURL);
        System.out.println("jsonParse... URL: " + url);

        System.out.println("jsonParse... setting up HTTP connection");
        HttpURLConnection request1 = (HttpURLConnection) url.openConnection();
        System.out.println("jsonParse... initiate GET request");
        request1.setRequestMethod("GET");
        request1.connect();
        System.out.println("jsonParse... connection successful");

        InputStream is = request1.getInputStream();
        BufferedReader bf_reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;

        try {
            System.out.println("jsonParse... appending json lines");
            while ((line = bf_reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            System.out.println("jsonParse... append completed");
        } catch (IOException e) { System.out.println("jsonParse... issue parsing json. " + e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
            }
        }

        String responseBody = sb.toString();
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(responseBody);
        System.out.println("jsonParse... parse of json completed");
        return (JSONObject)obj;
    }
}
