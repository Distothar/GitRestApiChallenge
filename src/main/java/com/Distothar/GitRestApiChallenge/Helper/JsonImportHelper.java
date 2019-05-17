package com.Distothar.GitRestApiChallenge.Helper;

import com.Distothar.GitRestApiChallenge.Entity.GitBranch;
import com.Distothar.GitRestApiChallenge.Entity.GitRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JsonImportHelper {

    public static JSONArray parseJsonObjectFromUrl(URL url)
    {
        BufferedReader reader = null;
        try
        {
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);
            return new JSONArray(buffer.toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static GitRepository parseJsonObjectAsGitRepository(JSONObject jsonObject)
    {
        try
        {
            String gitRepoName = jsonObject.getString("name");
            String gitRepoOwnerLogin = jsonObject.getJSONObject("owner").getString("login");

            return new GitRepository(gitRepoName, gitRepoOwnerLogin, null);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static List<GitBranch> parseJsonObjectAsGitBranchList(JSONArray jsonArray)
    {
        List<GitBranch> gitBranches = new ArrayList<GitBranch>();
        try
        {
            for(int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String branchName = jsonObject.getString("name");
                String lastCommitSha = jsonObject.getJSONObject("commit").getString("sha");
                GitBranch gitBranch = new GitBranch(branchName, lastCommitSha);
                gitBranches.add(gitBranch);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return gitBranches;
    }
}
