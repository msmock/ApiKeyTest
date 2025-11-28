package org.fnm.apikeytest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

    @GetMapping("/home")
    public String get() {
        return "Return the requested resource!";
    }

    @PostMapping("/home")
    public String post() {
        return "Return the requested resource!";
    }

}
