package com.Distothar.GitRestApiChallenge.Controller;

import com.Distothar.GitRestApiChallenge.Entity.GitRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class GitApiController {
    @GetMapping("/GetGitUser/{userName}")
    public List<GitRepository> getGitUserData(@PathVariable("userName") String userName,
                                              @RequestHeader("Accept") String headerInfo)
    {
        return null;
    }
}