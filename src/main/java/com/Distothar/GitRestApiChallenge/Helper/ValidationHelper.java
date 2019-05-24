package com.Distothar.GitRestApiChallenge.Helper;

import org.json.JSONArray;
import org.springframework.http.HttpStatus;

public class ValidationHelper {

    public static HttpStatus validateHttpStatusFromString(String status){
        if(status.contains("200 OK"))
            return HttpStatus.OK;
        if(status.contains("404 Not Found"))
            return HttpStatus.NOT_FOUND;
        if(status.contains("403 Forbidden"))
            return HttpStatus.FORBIDDEN;

        return null;
    }

    public static boolean validateJsonArray(JSONArray jsonArray) {
        if (jsonArray == null)
            return false;

        return true;
    }
}
