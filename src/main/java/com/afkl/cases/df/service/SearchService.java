package com.afkl.cases.df.service;

import com.afkl.cases.df.model.Fare;
import com.afkl.cases.df.model.Location;
import com.afkl.cases.df.model.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


@Component
public class SearchService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${service.mockurl}")
    String serviceUrl;

    @Value("${service.accessToken}")
    String accessToken;


    final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public Location searchAirport(String code){
        return restTemplate.getForObject(getAirportCodeUrilBuilder(code).toUriString(), Location.class);
    }

    public SearchResult searchFare(String sourceCode, String destinationCode) throws ExecutionException, InterruptedException {

        UriComponentsBuilder fareBuilder = uriBuilder("/fares/{origin_code}/{destination_code}");
        Map<String, String> fareParamsDest = new HashMap<>();
        fareParamsDest.put("origin_code", sourceCode);
        fareParamsDest.put("destination_code", destinationCode);


        final Future<Location> res1 = executorService.submit(new SearchTask(getAirportCodeUrilBuilder(sourceCode), restTemplate));
        final Future<Location> res2 = executorService.submit(new SearchTask(getAirportCodeUrilBuilder(destinationCode), restTemplate));
        final Future<Fare> res3 = executorService.submit(new FareTask(fareBuilder.buildAndExpand(fareParamsDest), restTemplate));

        SearchResult searchResult = new SearchResult();
        searchResult.setSource(res1.get().getDescription());
        searchResult.setDestination(res2.get().getDescription());
        searchResult.setPrice(res3.get().getAmount());
        searchResult.setCurrency(res3.get().getCurrency().name());

        return searchResult;
    }

    private UriComponents getAirportCodeUrilBuilder(String code){

        UriComponentsBuilder builder = uriBuilder("/airports/{code}");
        Map<String, String> uriParams = new HashMap<>();
        uriParams.put("code", code);
        return builder.buildAndExpand(uriParams);
    }

    private UriComponentsBuilder uriBuilder(String uri){
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(serviceUrl+uri);
        builder.queryParam("access_token", accessToken);
        return builder;

    }

}
