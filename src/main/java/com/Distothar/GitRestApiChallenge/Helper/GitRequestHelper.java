package com.Distothar.GitRestApiChallenge.Helper;

import com.Distothar.GitRestApiChallenge.Entity.GitBranch;
import com.Distothar.GitRestApiChallenge.Entity.GitRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class GitRequestHelper
{
    //ToDO
    public static List<GitRepository> getUserRepositories(String userName)
    {
        List<GitRepository> gitRepositories = new ArrayList<GitRepository>();
        try {
            URL url = new URL("https://api.github.com/users/mojombo/repos");
            JSONArray jsonArray = JsonImportHelper.parseJsonObjectFromUrl(url);
            if (jsonArray != null)
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (!jsonObject.getBoolean("fork"))
                    {
                        GitRepository gitRepo = JsonImportHelper.parseJsonObjectAsGitRepository(jsonObject);
                        if(gitRepo != null)
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
        //URL url = new URL("");
        return  null;
    }
}
