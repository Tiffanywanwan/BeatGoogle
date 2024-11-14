package com.example.searchengine;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

@RestController
public class SearchController {

    @GetMapping("/search")
    public String search(@RequestParam String keyword) throws IOException {
        GoogleQuery googleQuery = new GoogleQuery(keyword);
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper.writeValueAsString(googleQuery.query());
    }
}
