package com.Distothar.GitRestApiChallenge.Helper;

import com.Distothar.GitRestApiChallenge.Entity.GitBranch;
import com.Distothar.GitRestApiChallenge.Entity.GitRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class GitRequestHelper {

    public static List<GitRepository> getUserRepositoriesAndBranches(String userName) {
        List<GitRepository> gitRepositories = new ArrayList<GitRepository>();
        try {
            URL url = new URL("https://api.github.com/users/" + userName + "/repos");
            JSONArray jsonArray = JsonImportHelper.parseJsonObjectFromUrl(url);
            if (jsonArray != null)
                //ToDO using threads to iterate over the array
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (!jsonObject.getBoolean("fork")) {
                        GitRepository gitRepo = JsonImportHelper.parseJsonObjectAsGitRepository(jsonObject);
                        gitRepo.setBranches(getBranchesForRepository(userName, gitRepo.getRepositoryName()));
                        if (gitRepo != null)
                            gitRepositories.add(gitRepo);
                    }
                }
        }
        catch (IOException | JSONException e)
        {
            e.printStackTrace();
        }
        return gitRepositories;
    }

    public static List<GitBranch> getBranchesForRepository(String userName, String repositoryName)
    {
        List<GitBranch> gitBranches = new ArrayList<GitBranch>();
        try
        {
            URL url = new URL("https://api.github.com/repos/" + userName + "/" + repositoryName + "/branches");
            JSONArray jsonArray = JsonImportHelper.parseJsonObjectFromUrl(url);
            gitBranches = JsonImportHelper.parseJsonObjectAsGitBranchList(jsonArray);
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        return gitBranches;
    }
}