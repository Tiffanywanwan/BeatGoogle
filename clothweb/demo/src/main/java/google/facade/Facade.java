package google.facade;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import google.demo.service.SearchService;

@Component
public class Facade {

    @Autowired
    private SearchService searchService;

    public Map<String, String> performSearch(String query) {
        return searchService.search(query);
    }
}

