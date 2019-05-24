package com.Distothar.GitRestApiChallenge.Entity;

import java.util.List;

public class GitRepository
{
    private String repositoryName;
    private String ownerLogin;
    private List<GitBranch> branches;

    public GitRepository(String repositoryName, String ownerLogin, List<GitBranch> branches) {
        this.repositoryName = repositoryName;
        this.ownerLogin = ownerLogin;
        this.branches = branches;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public List<GitBranch> getBranches() {
        return branches;
    }

    public void setBranches(List<GitBranch> branches) {
        this.branches = branches;
    }
}
