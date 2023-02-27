package com.example.consumingrest.service;

import com.example.consumingrest.responsemodel.SimpleWeatherResponse;
import com.example.consumingrest.responsemodel.openweathermap.WeatherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    final RestTemplate restTemplate;
    final String baseUrl = "https://api.openweathermap.org/data/2.5/weather";
    private final WeatherCache weatherCache;
    Logger logger = LoggerFactory.getLogger(WeatherService.class);
    @Value("${apiKey:0}")
    private String apiKey;
//    private int cacheDuration = 2;
//    private LocalDateTime localDateTime;
//    private SimpleWeatherResponse simpleWeatherResponse;

    public WeatherService(RestTemplate restTemplate, WeatherCache weatherCache) {
        this.restTemplate = restTemplate;
        this.weatherCache = weatherCache;
    }

//    public SimpleWeatherResponse getSimpleWeatherResponse() {
//        LocalDateTime currentTime = LocalDateTime.now();
//        if (currentTime.minusMinutes(cacheDuration).isAfter(localDateTime) || simpleWeatherResponse == null || localDateTime == null){
//            localDateTime = LocalDateTime.now();
//            WeatherResponse weatherResponse = restTemplate.getForObject(
//                    baseUrl + "?q=Budapest,HU&appid=" + apiKey + "&units=metric&lang=hu", WeatherResponse.class);
//            simpleWeatherResponse = new SimpleWeatherResponse(weatherResponse);
//        }
//        return simpleWeatherResponse;
//    }


    public SimpleWeatherResponse getSimpleWeatherResponse() {
        return callApi("Budapest,HU");
    }

    // custom cache implementation
//    public SimpleWeatherResponse getSimpleWeatherResponse(String city) {
//        if (weatherCache.containsValidResponse(city)) {
//            logger.info("Reading data from the cache for '{}'", city);
//            return weatherCache.get(city);
//        }
//        logger.info("Reading data from the API for '{}'", city);
//        var response = callApi(city);
//        weatherCache.put(city, response);
//
//        return response;
//    }

    private SimpleWeatherResponse callApi(String city) {
        String url = baseUrl + "?q=" + city + "&appid=" + apiKey + "&units=metric&lang=hu";
        WeatherResponse weatherResponse = restTemplate.getForObject(
                url, WeatherResponse.class);
        return new SimpleWeatherResponse(weatherResponse);
    }

    // Spring cache
    @Cacheable("weather")
    public SimpleWeatherResponse getSimpleWeatherResponse(String city) {
        logger.info("API called to {}", city);
        return callApi(city);
    }
}
