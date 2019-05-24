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

    /**
     * parses a JSONArray from the given url
     * @param url
     * @return JSONArray
     * @throws IOException
     * @throws JSONException
     */
    public static JSONArray parseJsonArrayFromUrl(URL url) throws IOException, JSONException {
        BufferedReader reader = null;
        reader = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuffer buffer = new StringBuffer();
        int read;
        char[] chars = new char[1024];
        while ((read = reader.read(chars)) != -1)
            buffer.append(chars, 0, read);
        reader.close();

        JSONArray jsonArray = new JSONArray(buffer.toString());

        return jsonArray;
    }

    /**
     * Parse a JSONObject as GitRepository
     * @param jsonObject
     * @return GitRepository
     * @throws JSONException
     */
    public static GitRepository parseJsonObjectAsGitRepository(JSONObject jsonObject) throws JSONException {
        String gitRepoName = jsonObject.getString("name");
        String gitRepoOwnerLogin = jsonObject.getJSONObject("owner").getString("login");

        GitRepository repository = new GitRepository(gitRepoName, gitRepoOwnerLogin, null);

        return repository;
    }

    /**
     * Parse a JSONObject as List of GitBranch
     * @param jsonArray
     * @return List<GitBranch>
     * @throws JSONException
     */
    public static List<GitBranch> parseJsonObjectAsGitBranchList(JSONArray jsonArray) throws JSONException {
        List<GitBranch> gitBranches = new ArrayList<GitBranch>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String branchName = jsonObject.getString("name");
            String lastCommitSha = jsonObject.getJSONObject("commit").getString("sha");
            GitBranch gitBranch = new GitBranch(branchName, lastCommitSha);
            gitBranches.add(gitBranch);
        }

        return gitBranches;
    }
}
