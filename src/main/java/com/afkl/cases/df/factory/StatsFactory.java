package com.afkl.cases.df.factory;

import com.afkl.cases.df.model.Stats;

/**
 * Created by arvind on 04/01/17.
 */
public class StatsFactory {

    public static final Stats stats = new Stats();

    public static Stats getStats(){
        return stats;
    }
}
