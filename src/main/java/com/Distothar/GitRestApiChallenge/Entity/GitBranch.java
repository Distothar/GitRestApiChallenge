package com.Distothar.GitRestApiChallenge.Entity;

public class GitBranch
{
    private String branchName;
    private String commitSha;

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getCommitSha() {
        return commitSha;
    }

    public void setCommitSha(String commitSha) {
        this.commitSha = commitSha;
    }

    public GitBranch(String branchName, String commitSha) {
        this.branchName = branchName;
        this.commitSha = commitSha;
    }
}
