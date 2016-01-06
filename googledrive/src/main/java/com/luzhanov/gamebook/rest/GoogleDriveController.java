package com.luzhanov.gamebook.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GoogleDriveController {

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/list")
    public List<String> listAllFiles() {
        //todo: implement

        List<String> result = new ArrayList<>();
        result.add("aaa");
        result.add("bbb");

        return result;
    }

}
