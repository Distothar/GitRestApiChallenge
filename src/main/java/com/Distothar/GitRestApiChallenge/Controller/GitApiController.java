package com.Distothar.GitRestApiChallenge.Controller;

import com.Distothar.GitRestApiChallenge.Helper.GitRequestHelper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class GitApiController {

    @GetMapping(value = "/GetGitUser/{userName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getGitUserData(@PathVariable("userName") String userName,
                                                             @RequestHeader("Accept") String headerInfo)
    {
        //ToDO Validate RequestHeaderInfo

        return new ResponseEntity<Object>(GitRequestHelper.getUserRepositoriesAndBranches(userName), new HttpHeaders(), HttpStatus.OK);
    }

}