package com.Distothar.GitRestApiChallenge.Helper;

import com.Distothar.GitRestApiChallenge.Entity.GitBranch;
import com.Distothar.GitRestApiChallenge.Entity.GitRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GitRequestHelper {

    /**
     * gets UserData from Git
     * @param userName
     * @return ResponseEntity
     */
    public static ResponseEntity<Object> getGitUserData(String userName) {
        try {

            URL url = new URL("https://api.github.com/users/" + userName + "/repos");
            HttpStatus status = getGitApiRequestResponseStatus(url);

            if (status == HttpStatus.OK)
                return getData(userName, url);
            if (status == HttpStatus.NOT_FOUND)
                return RestErrorHandler.handleInvalidOperation("UserNotFound", status);
            if (status == HttpStatus.FORBIDDEN)
                return RestErrorHandler.handleInvalidOperation(status.toString(), status);
        }
        catch (Exception e)
        {
            return RestErrorHandler.handleUnexpectedError(e);
        }

        return null;
    }

    //region privateProperties

    /**
     * Connects to URL to check the responseStatus
     * @param url
     * @return HttpStatus of ResponseStatus
     * @throws IOException
     */
    public static HttpStatus getGitApiRequestResponseStatus(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        String statusField = connection.getHeaderField("Status");
        connection.disconnect();

        return determineHttpStatusFromString(statusField);
    }

    /**
     * Reads JsonArray from URL
     * @param userName
     * @param url
     * @return responseEntity containing an JsonArray of all Repositories of @userName
     * @throws JSONException
     * @throws IOException
     */
    private static ResponseEntity<Object> getData(String userName, URL url) throws JSONException, IOException {
        List<GitRepository> gitRepositories = new ArrayList<GitRepository>();

        JSONArray jsonArray = JsonImportHelper.parseJsonArrayFromUrl(url);
        if (!validateJsonArray(jsonArray))
            return RestErrorHandler.handleInvalidOperation("Unexpected Error", HttpStatus.INTERNAL_SERVER_ERROR);

        //ToDO using threads to iterate over the array
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (!jsonObject.getBoolean("fork")) {
                GitRepository gitRepo = JsonImportHelper.parseJsonObjectAsGitRepository(jsonObject);
                gitRepo.setBranches(getBranches(userName, gitRepo.getRepositoryName()));
                if (gitRepo != null)
                    gitRepositories.add(gitRepo);
            }
        }
        ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(gitRepositories, new HttpHeaders(), HttpStatus.OK);

        return responseEntity;
    }

    /**
     * Parses all Branches of the given repository
     * @param userName
     * @param repositoryName
     * @return List of GitBranches
     * @throws IOException
     * @throws JSONException
     */
    public static List<GitBranch> getBranches(String userName, String repositoryName) throws IOException, JSONException {
        List<GitBranch> gitBranches;

        URL url = new URL("https://api.github.com/repos/" + userName + "/" + repositoryName + "/branches");
        JSONArray jsonArray = JsonImportHelper.parseJsonArrayFromUrl(url);
        gitBranches = JsonImportHelper.parseJsonObjectAsGitBranchList(jsonArray);

        return gitBranches;
    }


    /**
     * Determine HttpStatus from the given String
     * @param status
     * @return HttpStatus
     */
    private static HttpStatus determineHttpStatusFromString(String status){
        if(status.contains("200 OK"))
            return HttpStatus.OK;
        if(status.contains("404 Not Found"))
            return HttpStatus.NOT_FOUND;
        if(status.contains("403 Forbidden"))
            return HttpStatus.FORBIDDEN;

        return null;
    }

    /**
     * Validates a JSONArray: returns true if it is valid, else false
     * @param jsonArray
     * @return boolean
     */
    private static boolean validateJsonArray(JSONArray jsonArray) {
        if (jsonArray == null)
            return false;

        return true;
    }
    //endregion
}