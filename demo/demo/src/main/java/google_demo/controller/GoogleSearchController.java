package google_demo.controller;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import google_demo.service.GoogleQueryService;

@RestController
public class GoogleSearchController {

    @Autowired
    private GoogleQueryService googleQueryService;

    @GetMapping(value = "/search", produces = "application/json")
public HashMap<String, String> search(@RequestParam String keyword) throws IOException {
    return googleQueryService.search(keyword);
}

}
