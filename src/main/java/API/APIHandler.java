package API;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

//this class currently uses URL queries
//the Wikipedia API was not happy when JSON was passed to it
public class APIHandler {

    //move  these to a better area
    private String baseURL = "https://en.wikipedia.org";
    private String searchParams = "/w/api.php?action=query&format=json&list=prefixsearch&pssearch=[tok]]";
    private String extractParams = "/w/api.php?action=query&format=json&prop=extracts&titles=[title]&formatversion=2&exsentences=10&exlimit=1&explaintext=1";

    //Currently returns just the text from a wiki page
    //this will be expanded to return both the link to a page and the text
    public String getWikiLink(final String searchValue) throws Exception {

        Gson gson = new Gson();
        String response = "";

        try {

            //build the URL for getting the initial list of pages
            String wikiURL = baseURL + searchParams;
            String  encodedToken = searchValue.replace(" ", "%20");
            wikiURL = wikiURL.replace("[tok]", encodedToken);

            //get the initial JSON for the search value
            response = getResponse(wikiURL);

            //replace continue with con, we can't name a variable continue
            response = response.replace("continue", "con");

            //get the prefix list from the generated object
            WikiSearchResponse wikiResponse = gson.fromJson(response, WikiSearchResponse.class);
            WikiSearchResponse.PrefixResult[] prefixList = wikiResponse.getQuery().getPrefixSearch();

            //build the URL to get the extract text
            wikiURL = baseURL + extractParams;
            wikiURL = wikiURL.replace("[title]", prefixList[0].getTitle().replace(" ", "%20"));
            response = getResponse(wikiURL);

            //get the first result returned by the API
            WikiExtractResponse extractResponse = gson.fromJson(response, WikiExtractResponse.class);
            WikiExtractResponse.Pages[] pagesList = extractResponse.getQuery().getPages();

            //if the result is empty, the API was unable to parse the page
            if(pagesList[0].getExtract().isEmpty()) {
                return String.format("Unable to parse page for %s", searchValue);
            }

            return pagesList[0].getExtract();
        }
        catch (Exception ex) {
            System.out.println(ex.toString());
            throw ex;
        }
    }

    //code to handle get requests when provided a URL
    private String getResponse(String wikiURL) throws Exception {

        //convert the string to a URL
        URL url = new URL(wikiURL);

        //open out connection and set the request method. This will always be get
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");

        //read the response
        Reader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder builder = new StringBuilder();
        for(int character; (character = in.read()) >=0;) {
            builder.append((char)character);
        }

        return builder.toString();
    }

}
