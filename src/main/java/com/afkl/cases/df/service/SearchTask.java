package com.afkl.cases.df.service;

import com.afkl.cases.df.model.Location;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.concurrent.Callable;


public class SearchTask implements Callable<Location> {

    private final UriComponents uriComponents;
    private final RestTemplate restTemplate;

    public SearchTask(UriComponents uriComponent, RestTemplate restTemplate) {
        this.uriComponents = uriComponent;
        this.restTemplate = restTemplate;
    }

    @Override
    public Location call() throws Exception {
        return  restTemplate.getForObject(uriComponents.toUriString(), Location.class);
    }
}
