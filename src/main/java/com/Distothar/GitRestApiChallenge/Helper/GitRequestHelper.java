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
    public static HttpStatus getGitApiRequestResponseStatus(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        String statusField = connection.getHeaderField("Status");
        connection.disconnect();

        return determineHttpStatusFromString(statusField);
    }

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


    public static List<GitBranch> getBranches(String userName, String repositoryName) throws IOException, JSONException {
        List<GitBranch> gitBranches;

        URL url = new URL("https://api.github.com/repos/" + userName + "/" + repositoryName + "/branches");
        JSONArray jsonArray = JsonImportHelper.parseJsonArrayFromUrl(url);
        gitBranches = JsonImportHelper.parseJsonObjectAsGitBranchList(jsonArray);

        return gitBranches;
    }
    private static HttpStatus determineHttpStatusFromString(String status){
        if(status.contains("200 OK"))
            return HttpStatus.OK;
        if(status.contains("404 Not Found"))
            return HttpStatus.NOT_FOUND;
        if(status.contains("403 Forbidden"))
            return HttpStatus.FORBIDDEN;

        return null;
    }
    private static boolean validateJsonArray(JSONArray jsonArray) {
        if (jsonArray == null)
            return false;

        return true;
    }
    //endregion
}