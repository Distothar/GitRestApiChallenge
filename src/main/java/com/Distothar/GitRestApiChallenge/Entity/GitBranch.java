package com.Distothar.GitRestApiChallenge.Entity;

public class GitBranch
{
    private String branchName;
    private String commitSha;

    public GitBranch(String branchName, String commitSha) {
        this.branchName = branchName;
        this.commitSha = commitSha;
    }

    public String getBranchName() {
        return branchName;
    }

    public String getCommitSha() {
        return commitSha;
    }
}
