package com.afkl.cases.df.model;

/**
 * Created by arvind on 04/01/17.
 */
public class Stats {

    private int numberOfRequests;
    private int failedRequests;
    private int successRequests;
    private long avResponseTime;
    private long maxResTime;
    private long minResponseTime;

    public int getNumberOfRequests() {
        return numberOfRequests;
    }

    public int getFailedRequests() {
        return failedRequests;
    }

    public int getSuccessRequests() {
        return successRequests;
    }

    public long getAvResponseTime() {
        return avResponseTime;
    }

    public long getMaxResTime() {
        return maxResTime;
    }

    public long getMinResponseTime() {
        return minResponseTime;
    }

    public void setStats(long responseTime, int statusCode){
        numberOfRequests = numberOfRequests+1;
        if(statusCode == 400){
            successRequests = successRequests+1;
        }else {
            failedRequests = failedRequests + 1;
        }

        avResponseTime = ((avResponseTime*numberOfRequests) + responseTime)/(numberOfRequests+1);
        maxResTime = responseTime > maxResTime ? responseTime : maxResTime;
        minResponseTime = minResponseTime == 0 || responseTime < minResponseTime ? responseTime : minResponseTime;
    }

}
