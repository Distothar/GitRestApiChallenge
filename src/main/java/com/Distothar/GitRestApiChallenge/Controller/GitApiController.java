package com.Distothar.GitRestApiChallenge.Controller;

import com.Distothar.GitRestApiChallenge.Helper.GitRequestHelper;
import com.Distothar.GitRestApiChallenge.Helper.RestErrorHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class GitApiController {

    @GetMapping(value = "/GetGitUser/{userName}")
    public ResponseEntity<Object> getGitUserData(@PathVariable("userName") String userName,
                                                 @RequestHeader("Accept") String headerInfo) {
        if (headerInfo.contains("application/json"))
            return GitRequestHelper.getGitUserData(userName);
        if (headerInfo.contains("application/xml"))
            return RestErrorHandler.handleInvalidOperation("application/xml is no acceptable header", HttpStatus.NOT_ACCEPTABLE);
        return null;
    }
}