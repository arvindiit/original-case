package com.afkl.cases.df.controller;

import com.afkl.cases.df.factory.StatsFactory;
import com.afkl.cases.df.model.Stats;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by arvind on 04/01/17.
 */
@RestController
public class StatsController {

    @RequestMapping("/stats")
    public Stats getStats(){
        return StatsFactory.getStats();
    }
}
