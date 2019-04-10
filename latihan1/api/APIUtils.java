package com.ukk.latihan1.api;

public class APIUtils {

    private APIUtils() {}

    public static String API_URL = "http://192.168.43.33/LatihanUKK/1/";

    public static UserService getUserService(){
        return RetrofitClient.getClient(API_URL).create(UserService.class);
    }
}
